package com.softwarefactorymm.notesofbooks.ui.main


import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.facebook.ads.*
import com.softwarefactorymm.notesofbooks.R


@SuppressLint("Registered")
class FragmentsHolderActivity : AppCompatActivity(), BackHandledFragment.BackHandlerInterface {
    private lateinit var  interstitialAd:InterstitialAd
    private lateinit var adView: AdView
    private val TAG = FragmentsHolderActivity::class.java!!.getSimpleName()
    companion object {
        val EXTRA_DISPLAY_FRAGMENT = "FragmentHolderActivity.EXTRA_DISPLAY_FRAGMENT"
    }
    lateinit var frameLayout: FrameLayout
    var newFragment: Fragment? = null
    private  var fragmentName:String? = null
    private var selectedFragment: BackHandledFragment? = null

    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragments_holder)
        if (Build.VERSION.SDK_INT < 16) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }


        fragmentName = intent.getStringExtra(EXTRA_DISPLAY_FRAGMENT)

        if (PostDetailsFragment::class.java.simpleName == fragmentName){
            newFragment = PostDetailsFragment()
        }
//        if (TutorialDetailsFragment::class.java.simpleName==fragmentName){
//            newFragment = TutorialDetailsFragment()
//        }


        if (newFragment != null) {
            val fm = supportFragmentManager
            val fragment = fm.findFragmentById(R.id.main_holderFragment)
            if (fragment == null) {
                fm.beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_bottom,R.anim.exit_to_bottom)
                    .add(R.id.main_holderFragment, newFragment!!).commit()
            } else {
                fm.beginTransaction()
                    .replace(R.id.main_holderFragment, newFragment!!).commit()
            }
        } else {
            this@FragmentsHolderActivity.finish()
        }
        adView = AdView(this, "308857789831112_308868776496680", AdSize.BANNER_HEIGHT_50)

        // Find the Ad Container
        val adContainer = findViewById<View>(R.id.banner_containerHo) as LinearLayout

        // Add the ad view to your activity layout
        adContainer.addView(adView)

        // Request an ad
        adView.loadAd()

        interstitialAd = InterstitialAd(this, "308857789831112_308877703162454")
        interstitialAd.setAdListener(object : InterstitialAdListener {
            override fun onInterstitialDisplayed(ad: Ad) {
                // Interstitial ad displayed callback
                Log.e(TAG, "Interstitial ad displayed.")
            }

            override fun onInterstitialDismissed(ad: Ad) {
                // Interstitial dismissed callback
                Log.e(TAG, "Interstitial ad dismissed.")
            }

            override fun onError(ad: Ad, adError: AdError) {
                // Ad error callback
                Log.e(TAG, "Interstitial ad failed to load: " + adError.errorMessage)
            }

            override fun onAdLoaded(ad: Ad) {
                // Interstitial ad is loaded and ready to be displayed
                Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!")
                // Show the ad

                showAdWithDelay()
            }

            override fun onAdClicked(ad: Ad) {
                // Ad clicked callback
                Log.d(TAG, "Interstitial ad clicked!")
            }

            override fun onLoggingImpression(ad: Ad) {
                // Ad impression logged callback
                Log.d(TAG, "Interstitial ad impression logged!")
            }
        })

        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown
        interstitialAd.loadAd()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun setmSelectedFragment(backHandledFragment: BackHandledFragment) {
        if (selectedFragment != null) {
        }
        selectedFragment = backHandledFragment
    }
    private fun showAdWithDelay() {
        /**
         * Here is an example for displaying the ad with delay;
         * Please do not copy the Handler into your project
         */
        val handler = Handler()
        handler.postDelayed(Runnable {
            // Check if interstitialAd has been loaded successfully
            if (!interstitialAd.isAdLoaded) {
                return@Runnable
            }
            // Check if ad is already expired or invalidated, and do not show ad if that is the case. You will not get paid to show an invalidated ad.
            if (interstitialAd.isAdInvalidated) {
                return@Runnable
            }
            // Show the ad
            interstitialAd.show()
        }, (1000 * 40).toLong()) // Show the ad after 15 minutes
    }
}