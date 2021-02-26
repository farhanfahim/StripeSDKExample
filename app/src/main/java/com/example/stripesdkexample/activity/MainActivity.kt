package com.example.stripesdkexample.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.stripesdkexample.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        buttonStripeCardPayment.setOnClickListener {
            var intent = Intent(this@MainActivity, PreBuiltUIActivity::class.java)
            intent.putExtra("customerId","YOUR_CUSTOMER_ID")
            startActivity(intent)
            overridePendingTransition(R.anim.right_to_left, 0)
        }
    }
}
