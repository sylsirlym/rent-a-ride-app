package com.skills.rentaride.ui

import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.skills.rentaride.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val spiner = findViewById<AppCompatImageView>(R.id.imageViewLoader)

        imageLoder(spiner)

        val background = object : Thread(){
            override fun run() {
                try {
                    Thread.sleep(3000)
                    val intent = Intent(baseContext, MainActivity::class.java)
                    startActivity(intent)
                } catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }
        background.start()
    }
    fun imageLoder(imageViewLoader: AppCompatImageView) {
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