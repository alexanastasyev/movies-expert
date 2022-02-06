package com.alexanastasyev.mymovies.screens.buy

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.alexanastasyev.mymovies.R
import com.alexanastasyev.mymovies.internet.ads.AdsManager
import com.alexanastasyev.mymovies.internet.inapp.BillingManager

class BuyActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy_no_ads)
        initDisclaimer()
        initButton()
        initCancel()
    }

    private fun initDisclaimer() {
        findViewById<TextView>(R.id.disclaimer).text = String.format(
            getString(R.string.disclaimer),
            BillingManager.getPrice()
        )
    }

    private fun initButton() {
        findViewById<Button>(R.id.buy_no_ads).setOnClickListener {
            BillingManager.buyNoAds(this) {
                AdsManager.adsAvailable = false
                this.finish()
            }
        }
    }

    private fun initCancel() {
        findViewById<ImageView>(R.id.buy_cancel).setOnClickListener {
            this.finish()
        }
    }

}