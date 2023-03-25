package com.developeralamin.eshop.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.developeralamin.eshop.adapter.ProductAdapter
import com.developeralamin.eshop.api.ApiInterface
import com.developeralamin.eshop.api.ApiUtilities
import com.developeralamin.eshop.databinding.ActivityMainBinding
import com.developeralamin.eshop.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
   lateinit var list: List<Product>
    lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        list = ArrayList()
        adapter = ProductAdapter(this@MainActivity, list)

        lifecycleScope.launch(Dispatchers.IO) {
            val res = ApiUtilities.getInstance().create(ApiInterface::class.java).getProductData()
            withContext(Dispatchers.Main) {
                binding.productreecyclerView.adapter =
                    ProductAdapter(this@MainActivity, res.body()!!.products)
                binding.progressBarId.visibility=GONE
                binding.editText.visibility= VISIBLE
            }
            Log.d("SAKIB", "ProductData" + res.body())
        }

        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filter(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    private fun filter(text: String) {
        val filterList: ArrayList<Product> = ArrayList()
        for (item in list) {
            if (item.title.toLowerCase().contains(text.toLowerCase())) {
                filterList.add(item)
            }
        }

        adapter.FilteredList(filterList)
    }
}