package com.developeralamin.eshop.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.developeralamin.eshop.databinding.ActivityProductBinding
import com.developeralamin.eshop.model.Product
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject

class ProductActivity : AppCompatActivity(), PaymentResultListener {

    lateinit var binding: ActivityProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val id = intent.getStringExtra("id")
        val image = intent.getStringExtra("image")
        val price = intent.getStringExtra("price")
        //binding.textView1.setText(id)
        Log.d("IMAGE", "IMAGES " + image)

        var images = image!!.replace("[","")
        images = images.replace("]","")
        val imageList = images.split(", ")

        val slidList = ArrayList<SlideModel>()
        imageList.forEach {
            slidList.add(SlideModel(it))
        }
        binding.imageSlider.setImageList(slidList)

        binding.buyId.setOnClickListener {

            val checkout = Checkout()
            checkout.setKeyID("rzp_test_YMpRNrRlG6VDeZ")

            try {
                val options = JSONObject()
                options.put("name", "Eshop")
                options.put("description", "Best Ecommerce App")
                options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
                options.put("theme.color", "#673AB7")
                options.put("currency", "BDT")
                options.put("amount", (price!!.toInt() * 100)) //pass amount in currency subunits
                options.put("prefill.email", "alaminsakib.cse@gmail.com")
                options.put("prefill.contact", "+8801929284244")
                checkout.open(this, options)
            } catch (e: Exception) {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onPaymentSuccess(p0: String?) {
        binding.textView1.text=p0
        binding.textView1.setTextColor(Color.GREEN)
        Toast.makeText(this, "Payment Success", Toast.LENGTH_SHORT).show()
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        binding.textView1.text=p1
        binding.textView1.setTextColor(Color.RED)
        Toast.makeText(this, "Payment Error", Toast.LENGTH_SHORT).show()
    }
}
