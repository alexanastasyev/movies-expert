package com.alexanastasyev.mymovies.internet.ads

import android.app.Activity
import android.util.DisplayMetrics
import android.widget.FrameLayout
import com.google.android.gms.ads.*
import com.alexanastasyev.mymovies.R
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

object AdsManager {

    private const val ADS_LOADING_DELAY = 100L

    var adsAvailable: Boolean? = null
    private val adRequest: AdRequest = AdRequest.Builder().build()
    private val compositeDisposable = CompositeDisposable()

    fun loadBannerIfAvailable(bannerLayout: FrameLayout, activity: Activity) {
        checkAdsAvailable { adsAvailable ->
            if (adsAvailable) {
                loadBanner(bannerLayout, activity)
            }
        }
    }

    private fun loadBanner(bannerLayout: FrameLayout, activity: Activity) {
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

    fun disposeAll() {
        compositeDisposable.dispose()
    }

    fun checkAdsAvailable(onFinish: (Boolean) -> Unit) {
        val disposable = Single.fromCallable {
            while (adsAvailable == null) {
                Thread.sleep(ADS_LOADING_DELAY)
            }
            adsAvailable!!
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onFinish(it)
            }, {})
        compositeDisposable.add(disposable)
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