package com.example.stripesdkexample.utils

import android.content.Context
import android.util.Log
import androidx.annotation.Size
import com.example.stripesdkexample.R
import com.stripe.android.EphemeralKeyProvider
import com.stripe.android.EphemeralKeyUpdateListener
import com.stripe.model.EphemeralKey
import com.stripe.net.RequestOptions
import kotlinx.coroutines.*
import java.util.*


class EphemeralKeyProvider(val context: Context,var customerId:String) : EphemeralKeyProvider {
    override fun createEphemeralKey(
        @Size(min = 4) apiVersion: String,
        keyUpdateListener: EphemeralKeyUpdateListener
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            val ephemeralKey = async(Dispatchers.IO) { ephemeralKeyProvider(apiVersion, context,customerId) }
            val ephemeralKeyJson = ephemeralKey.await()
            Log.e("ephemeralKey", ephemeralKeyJson)
            keyUpdateListener.onKeyUpdate(ephemeralKeyJson)
        }
    }

    private suspend fun ephemeralKeyProvider(apiVersion: String, context: Context,customerId:String): String {
        return GlobalScope.async(Dispatchers.IO) {
            com.stripe.Stripe.apiKey = context.resources.getString(R.string.stripe_secret_key)
            val requestOptions: RequestOptions = RequestOptions.RequestOptionsBuilder()
                .setStripeVersionOverride(apiVersion)
                .build()
            val options: MutableMap<String, Any> =
                HashMap()
            //Here I'm directly hardcoded the Stripe Customer key.
            //You can find the clear flow of creating the Customer in Server side code in https://stripe.com/docs/api/customers/create?lang=java
            options["customer"] = customerId
            val key: EphemeralKey = EphemeralKey.create(options, requestOptions)
            return@async key.rawJson.toString()
        }.await()
    }
}