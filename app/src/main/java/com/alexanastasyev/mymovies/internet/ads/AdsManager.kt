package com.alexanastasyev.mymovies.internet.ads

import android.app.Activity
import android.util.DisplayMetrics
import android.widget.FrameLayout
import com.google.android.gms.ads.*
import com.alexanastasyev.mymovies.R

object AdsManager {

    private val adRequest: AdRequest = AdRequest.Builder().build()

    fun loadBanner(bannerLayout: FrameLayout, activity: Activity) {
        var initialLayoutComplete = false
        val adView = AdView(activity)

        bannerLayout.addView(adView)
        bannerLayout.viewTreeObserver.addOnGlobalLayoutListener {
            if (!initialLayoutComplete) {
                initialLayoutComplete = true
                adView.adUnitId = activity.getString(R.string.banner_id)
                adView.adSize = calculateBannerSize(bannerLayout, activity)
                adView.loadAd(adRequest)
            }
        }
    }

    private fun calculateBannerSize(bannerLayout: FrameLayout, activity: Activity): AdSize {
        val display = activity.windowManager.defaultDisplay
        val outMetrics = DisplayMetrics()
        display.getMetrics(outMetrics)

        var adWidthPixels = bannerLayout.width.toFloat()
        if (adWidthPixels == 0f) {
            adWidthPixels = outMetrics.widthPixels.toFloat()
        }

        val adWidth = (adWidthPixels / outMetrics.density).toInt()
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth)
    }
}