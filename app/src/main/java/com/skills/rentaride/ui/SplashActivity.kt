package com.skills.rentaride.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.skills.rentaride.R
import com.skills.rentaride.di.DaggerApiComponent
import com.skills.rentaride.network.service.RentARideService
import com.skills.rentaride.utils.SharedPrefManager
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {
    private val TAG = "SplashActivity"

    @Inject
    lateinit var rentARideService: RentARideService

    init {
        DaggerApiComponent.create()
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val spinner = findViewById<AppCompatImageView>(R.id.imageViewLoader)

        imageLoader(spinner)

        val background = object : Thread() {
            override fun run() {
                try {
                    sleep(3000)
                    val profile: String = SharedPrefManager(this@SplashActivity).getSharedPrefManager(this@SplashActivity)!!.getString("profile")
                    var intent = Intent(baseContext, MainActivity::class.java)
                    if (profile.isNotBlank()){
                        Log.i(TAG, "Retrieved Profile: $profile");
                        intent = Intent(baseContext, HomeActivity::class.java)
                    }
                    startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        if (rentARideService.isNetworkAvailable(this@SplashActivity)) {
            Log.i(TAG, "Network is available");
            background.start()
        } else {
            Log.i(TAG, "Network is not available");
            Toast.makeText(this@SplashActivity, "Check Internet Connection !!!", Toast.LENGTH_SHORT)
                .show()
        }

    }

    private fun imageLoader(imageViewLoader: AppCompatImageView) {
        val rotate = RotateAnimation(
            0F, 359F,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        )
        rotate.duration = 3000
        rotate.interpolator = LinearInterpolator()
        rotate.repeatCount = Animation.INFINITE
        imageViewLoader.startAnimation(rotate)
    }
}