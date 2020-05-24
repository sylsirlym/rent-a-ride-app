package com.skills.rentaride.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.skills.rentaride.R
import com.skills.rentaride.model.ResponseDTO
import com.skills.rentaride.utils.SharedPrefManager
import kotlinx.android.synthetic.main.success.*


class SuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.success)

        try {
            val response = intent.getStringExtra("response")
            val responseDto = parseResponse(response)
            val bannerView = this.banner
            val layout = findViewById<View>(R.id.background) as RelativeLayout

            //Set defaults
            var statusText = "SUCCESS"
            var  image = R.drawable.success
            //Evaluate success
            if(responseDto?.statusCode != 200){
                statusText = "FAILED"
                image=R.drawable.failed
            }

            //Set text
            bannerView.text = statusText

            //Set Image
            val sdk : Int = android.os.Build.VERSION.SDK_INT
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                layout.setBackgroundDrawable(ContextCompat.getDrawable(this, image))
            } else {
                layout.setBackground(ContextCompat.getDrawable(this, image))
            }

            val intent = Intent(baseContext, HomeActivity::class.java)
            startActivity(intent)

        } catch (ex :Exception){
            Log.e("TAG", "Exception "+ex.message)
            Log.wtf("TAG", ex)
        }

    }

    private fun parseResponse(response : String): ResponseDTO? {
        Log.e("TAG", "Response $response")

        val gson = Gson()
        return gson.fromJson(response, ResponseDTO::class.java)
    }

}