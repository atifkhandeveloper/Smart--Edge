package com.abh80.smartedge.flashalert


import android.app.Activity
import android.app.Application
import com.google.android.material.color.DynamicColors
import com.abh80.smartedge.flashalert.db.AppDatabase
import com.abh80.smartedge.flashalert.tools.ThemeSelector
import com.google.android.gms.ads.MobileAds


class FlashAlert: Application() {



//    private var appOpenAdManager: com.abh80.smartedge.flashalert.FlashAlert.AppOpenAdManager? = null

    private var currentActivity: Activity? = null

    companion object {
        var isRunning = false
    }

    override fun onCreate() {
        super.onCreate()
        ThemeSelector.setTheme(this)
        AppDatabase.getAppDataBase(this)
        DynamicColors.applyToActivitiesIfAvailable(this)

//        registerActivityLifecycleCallbacks(this)
        MobileAds.initialize(
            this
        ) { }

//        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
//        appOpenAdManager = com.abh80.smartedge.flashalert.FlashAlert.AppOpenAdManager()

    }
}

//    @OnLifecycleEvent(Lifecycle.Event.ON_START)
//    protected fun onMoveToForeground() {
//        // Show the ad (if available) when the app moves to foreground.
//        appOpenAdManager!!.showAdIfAvailable(currentActivity!!)
//    }
//
//    /** ActivityLifecycleCallback methods.  */
//    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
//
//    override fun onActivityStarted(activity: Activity) {
//        // An ad activity is started when an ad is showing, which could be AdActivity class from Google
//        // SDK or another activity class implemented by a third party mediation partner. Updating the
//        // currentActivity only when an ad is not showing will ensure it is not an ad activity, but the
//        // one that shows the ad.
//        if (!appOpenAdManager!!.isShowingAd) {
//            currentActivity = activity
//        }
//    }
//
//    override fun onActivityResumed(activity: Activity) {}
//
//    override fun onActivityPaused(activity: Activity) {}
//
//    override fun onActivityStopped(activity: Activity) {}
//
//    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
//
//    override fun onActivityDestroyed(activity: Activity) {}
//
//    /**
//     * Shows an app open ad.
//     *
//     * @param activity the activity that shows the app open ad
//     * @param onShowAdCompleteListener the listener to be notified when an app open ad is complete
//     */
//    fun showAdIfAvailable(
//        activity: Activity,
//        onShowAdCompleteListener: OnShowAdCompleteListener,
//    ) {
//        // We wrap the showAdIfAvailable to enforce that other classes only interact with MyApplication
//        // class.
//        appOpenAdManager!!.showAdIfAvailable(activity, onShowAdCompleteListener)
//    }
//
//    /**
//     * Interface definition for a callback to be invoked when an app open ad is complete
//     * (i.e. dismissed or fails to show).
//     */
//    interface OnShowAdCompleteListener {
//        fun onShowAdComplete()
//    }
//
//    /** Inner class that loads and shows app open ads.  */
//    private class AppOpenAdManager
//    /** Constructor.  */
//    {
//        private var appOpenAd: AppOpenAd? = null
//        private var isLoadingAd = false
//        var isShowingAd = false
//
//        /** Keep track of the time an app open ad is loaded to ensure you don't show an expired ad.  */
//        private var loadTime: Long = 0
//
//        /**
//         * Load an ad.
//         *
//         * @param context the context of the activity that loads the ad
//         */
//        private fun loadAd(context: Context) {
//            // Do not load ad if there is an unused ad or one is already loading.
//            if (isLoadingAd || isAdAvailable) {
//                return
//            }
//            isLoadingAd = true
//            val request = AdRequest.Builder().build()
//            AppOpenAd.load(context, Resources.getSystem().getString(R.string.app_open_id),
//                request,
//                AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
//                object : AppOpenAdLoadCallback() {
//                    /**
//                     * Called when an app open ad has loaded.
//                     *
//                     * @param ad the loaded app open ad.
//                     */
//                    override fun onAdLoaded(ad: AppOpenAd) {
//                        appOpenAd = ad
//                        isLoadingAd = false
//                        loadTime = Date().time
//                        Log.d(LOG_TAG, "onAdLoaded.")
//                        Toast.makeText(context, "onAdLoaded", Toast.LENGTH_SHORT).show()
//                    }
//
//                    /**
//                     * Called when an app open ad has failed to load.
//                     *
//                     * @param loadAdError the error.
//                     */
//                    override fun onAdFailedToLoad(loadAdError: LoadAdError) {
//                        isLoadingAd = false
//                        Log.d(LOG_TAG, "onAdFailedToLoad: " + loadAdError.message)
//                        Toast.makeText(context, "onAdFailedToLoad", Toast.LENGTH_SHORT).show()
//                    }
//                })
//        }
//
//        /** Check if ad was loaded more than n hours ago.  */
//        private fun wasLoadTimeLessThanNHoursAgo(numHours: Long): Boolean {
//            val dateDifference = Date().time - loadTime
//            val numMilliSecondsPerHour: Long = 3600000
//            return dateDifference < numMilliSecondsPerHour * numHours
//        }// Ad references in the app open beta will time out after four hours, but this time limit
//        // may change in future beta versions. For details, see:
//        // https://support.google.com/admob/answer/9341964?hl=en
//        /** Check if ad exists and can be shown.  */
//        private val isAdAvailable: Boolean
//            private get() =// Ad references in the app open beta will time out after four hours, but this time limit
//            // may change in future beta versions. For details, see:
//                // https://support.google.com/admob/answer/9341964?hl=en
//                appOpenAd != null && wasLoadTimeLessThanNHoursAgo(4)
//        /**
//         * Show the ad if one isn't already showing.
//         *
//         * @param activity the activity that shows the app open ad
//         * @param onShowAdCompleteListener the listener to be notified when an app open ad is complete
//         */
//        /**
//         * Show the ad if one isn't already showing.
//         *
//         * @param activity the activity that shows the app open ad
//         */
//        fun showAdIfAvailable(
//            activity: Activity,
//            onShowAdCompleteListener: OnShowAdCompleteListener =
//                object : OnShowAdCompleteListener {
//                    override fun onShowAdComplete() {
//                        // Empty because the user will go back to the activity that shows the ad.
//                    }
//                },
//        ) {
//            // If the app open ad is already showing, do not show the ad again.
//            if (isShowingAd) {
//                Log.d(LOG_TAG, "The app open ad is already showing.")
//                return
//            }
//
//            // If the app open ad is not available yet, invoke the callback then load the ad.
//            if (!isAdAvailable) {
//                Log.d(LOG_TAG, "The app open ad is not ready yet.")
//                onShowAdCompleteListener.onShowAdComplete()
//                loadAd(activity)
//                return
//            }
//            Log.d(LOG_TAG, "Will show ad.")
//            appOpenAd!!.fullScreenContentCallback = object : FullScreenContentCallback() {
//                /** Called when full screen content is dismissed.  */
//                override fun onAdDismissedFullScreenContent() {
//                    // Set the reference to null so isAdAvailable() returns false.
//                    appOpenAd = null
//                    isShowingAd = false
//                    Log.d(LOG_TAG, "onAdDismissedFullScreenContent.")
//                    Toast.makeText(activity, "onAdDismissedFullScreenContent", Toast.LENGTH_SHORT)
//                        .show()
//                    onShowAdCompleteListener.onShowAdComplete()
//                    loadAd(activity)
//                }
//
//                /** Called when fullscreen content failed to show.  */
//                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
//                    appOpenAd = null
//                    isShowingAd = false
//                    Log.d(LOG_TAG, "onAdFailedToShowFullScreenContent: " + adError.message)
//                    Toast.makeText(
//                        activity,
//                        "onAdFailedToShowFullScreenContent",
//                        Toast.LENGTH_SHORT
//                    )
//                        .show()
//                    onShowAdCompleteListener.onShowAdComplete()
//                    loadAd(activity)
//                }
//
//                /** Called when fullscreen content is shown.  */
//                override fun onAdShowedFullScreenContent() {
//                    Log.d(LOG_TAG, "onAdShowedFullScreenContent.")
//                    Toast.makeText(activity, "onAdShowedFullScreenContent", Toast.LENGTH_SHORT)
//                        .show()
//                }
//            }
//            isShowingAd = true
//            appOpenAd!!.show(activity)
//        }
//
//        companion object {
//            private const val LOG_TAG = "AppOpenAdManager"
//            private const val AD_UNIT_ID = "ca-app-pub-3940256099942544/3419835294"
//        }
//    }

