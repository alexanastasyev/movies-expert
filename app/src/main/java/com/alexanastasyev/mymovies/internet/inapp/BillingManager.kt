package com.alexanastasyev.mymovies.internet.inapp

import android.app.Activity
import com.alexanastasyev.mymovies.internet.ads.AdsManager
import com.android.billingclient.api.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object BillingManager {
    private val NO_ADS_SKU = "no_ads"
    private var onPurchased: () -> Unit = {}
    private var skuDetails: SkuDetails? = null
    private lateinit var billingClient: BillingClient

    fun init(activity: Activity) {
        val purchasesUpdatedListener =
            PurchasesUpdatedListener { billingResult, purchases ->
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
                    for (purchase in purchases) {
                        CoroutineScope(Dispatchers.IO).launch {
                            handlePurchase(purchase)
                        }
                    }
                }
            }

        billingClient = BillingClient.newBuilder(activity)
            .setListener(purchasesUpdatedListener)
            .enablePendingPurchases()
            .build()

        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    CoroutineScope(Dispatchers.IO).launch {
                        val skuDetailsList = querySkuDetails().skuDetailsList
                        skuDetails = if (skuDetailsList.isNullOrEmpty()) {
                            null
                        } else {
                            skuDetailsList[0]
                        }
                        checkBuying()
                    }
                }
            }

            override fun onBillingServiceDisconnected() {
            }
        })
    }

    fun buyNoAds(activity: Activity, onPurchased: () -> Unit) {
        this.onPurchased = onPurchased

        val flowParams = skuDetails?.let { details ->
            BillingFlowParams.newBuilder()
                .setSkuDetails(details)
                .build()
        }
        if (flowParams != null) {
            billingClient.launchBillingFlow(activity, flowParams).responseCode
        }
    }

    fun getPrice(): String {
        return skuDetails?.price ?: ""
    }

    private suspend fun handlePurchase(purchase: Purchase) {
        withContext(Dispatchers.Main) {
            onPurchased()
        }

        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
            if (!purchase.isAcknowledged) {
                val acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
                    .setPurchaseToken(purchase.purchaseToken)
                withContext(Dispatchers.IO) {
                    billingClient.acknowledgePurchase(acknowledgePurchaseParams.build())
                }
            }
        }
    }

    private suspend fun querySkuDetails(): SkuDetailsResult {
        val skuList = ArrayList<String>()
        skuList.add(NO_ADS_SKU)
        val params = SkuDetailsParams.newBuilder()
        params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP)

        return withContext(Dispatchers.IO) {
            billingClient.querySkuDetails(params.build())
        }
    }

    private suspend fun checkBuying() {
        val skuList = ArrayList<String>()
        skuList.add(NO_ADS_SKU)
        val params = SkuDetailsParams.newBuilder()
        params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP)

        withContext(Dispatchers.IO) {
            billingClient.queryPurchasesAsync(BillingClient.SkuType.INAPP) { _, p1 ->
                if (p1.isNotEmpty()) {
                    AdsManager.adsAvailable = !((p1[0].skus[0] == NO_ADS_SKU) &&
                            (p1[0].isAcknowledged) &&
                            (p1[0].purchaseState == Purchase.PurchaseState.PURCHASED))
                } else {
                    AdsManager.adsAvailable = true
                }
            }
        }
    }
}