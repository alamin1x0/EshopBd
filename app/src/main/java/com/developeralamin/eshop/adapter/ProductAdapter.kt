package com.developeralamin.eshop.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.developeralamin.eshop.R
import com.developeralamin.eshop.databinding.ItemProductLayoutBinding
import com.developeralamin.eshop.databinding.LayoutProductItemBinding
import com.developeralamin.eshop.model.Product
import com.developeralamin.eshop.model.ProductModel
import com.developeralamin.eshop.ui.MainActivity
import com.developeralamin.eshop.ui.PaymentGatway
import com.developeralamin.eshop.ui.ProductActivity

class ProductAdapter(val mainActivity: MainActivity, var list: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = LayoutProductItemBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            LayoutInflater.from(mainActivity).inflate(R.layout.layout_product_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = list[position]

        holder.binding.textView2.text = item.title.toString()
        holder.binding.textView3.rating = item.rating.toFloat()
        holder.binding.button.text = "৳" + item.discountPercentage.toString()
        holder.binding.button2.text = "৳" + item.price.toString()
        Glide.with(mainActivity).load(item.thumbnail)
            .thumbnail(Glide.with(mainActivity).load(R.drawable.spinner))
            .into(holder.binding.imageView2)

        holder.binding.button2.setOnClickListener {
            val intent = Intent(Intent(mainActivity, PaymentGatway::class.java))
            intent.putExtra("id", item.id.toString())
            intent.putExtra("image", item.images.toString())
            intent.putExtra("price", item.price.toString())
            mainActivity.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun FilteredList(filterList: ArrayList<Product>) {
        list = filterList
        notifyDataSetChanged()
    }
}