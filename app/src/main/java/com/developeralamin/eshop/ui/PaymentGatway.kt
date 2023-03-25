package com.developeralamin.eshop.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.developeralamin.eshop.R
import com.developeralamin.eshop.databinding.ActivityPaymentGatwayBinding
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCAdditionalInitializer
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCommerzInitialization
import com.sslwireless.sslcommerzlibrary.model.response.SSLCTransactionInfoModel
import com.sslwireless.sslcommerzlibrary.model.util.SSLCCurrencyType
import com.sslwireless.sslcommerzlibrary.model.util.SSLCSdkType
import com.sslwireless.sslcommerzlibrary.view.singleton.IntegrateSSLCommerz
import com.sslwireless.sslcommerzlibrary.viewmodel.listener.SSLCTransactionResponseListener

class PaymentGatway : AppCompatActivity(), SSLCTransactionResponseListener {

    lateinit var binding: ActivityPaymentGatwayBinding

    lateinit var sslCommerzInitialization: SSLCommerzInitialization
    lateinit var additionalInitializer: SSLCAdditionalInitializer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentGatwayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val price = intent.getStringExtra("price")

        binding.paymentBtn.setOnClickListener {
            sslCommerzInitialization = SSLCommerzInitialization(
                "nexde63e48d9521823",
                "nexde63e48d9521823@ssl",
                price!!.toDouble(), SSLCCurrencyType.BDT,
                "TrxId_SSL$price",
                "yourProductType", SSLCSdkType.TESTBOX
            )
            additionalInitializer = SSLCAdditionalInitializer()
            additionalInitializer!!.valueA = "Value Option 1"
            additionalInitializer!!.valueB = "Value Option 1"
            additionalInitializer!!.valueC = "Value Option 1"
            additionalInitializer!!.valueD = "Value Option 1"
            IntegrateSSLCommerz
                .getInstance(this)
                .addSSLCommerzInitialization(sslCommerzInitialization)
                .addAdditionalInitializer(additionalInitializer)
                .buildApiCall(this)
        }


    }

    override fun transactionSuccess(p0: SSLCTransactionInfoModel?) {
        Toast.makeText(applicationContext, "Payment Successfully", Toast.LENGTH_SHORT).show()
    }

    override fun transactionFail(p0: String?) {
        Toast.makeText(applicationContext, p0.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun closed(p0: String?) {
        Toast.makeText(applicationContext, p0.toString(), Toast.LENGTH_SHORT).show()
    }
}

