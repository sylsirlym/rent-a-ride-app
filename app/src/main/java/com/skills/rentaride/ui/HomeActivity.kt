package com.skills.rentaride.ui

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.skills.rentaride.R
import com.skills.rentaride.databinding.FragmentHomeBinding
import com.skills.rentaride.di.DaggerApiComponent
import com.skills.rentaride.model.ProfileDTO
import com.skills.rentaride.model.ResponseDTO
import com.skills.rentaride.network.service.RentARideService
import io.reactivex.Single
import javax.inject.Inject

class HomeActivity : AppCompatActivity(){
    private val TAG = "HomeActivity"
    @Inject
    lateinit var rentARideService: RentARideService

    init {
        DaggerApiComponent.create()
            .inject(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.fragment_home)
        val binding: FragmentHomeBinding = DataBindingUtil.setContentView(
            this, R.layout.fragment_home)

        try {
            val profile = intent.getStringExtra("profileDetails")
            Log.i(TAG, profile)
            val gson = Gson()
            val profileDetails = gson.fromJson(profile, ProfileDTO::class.java)
            Log.i(TAG, profileDetails.fname)
            binding.profile=profileDetails;

            Log.i(TAG, "About to fetch Transaction History")
            val responseDTO : Single<ResponseDTO> = rentARideService.getLendTransactionHistory(profileDetails.msisdn)
            Log.i(TAG, "Transaction History"+responseDTO.blockingGet().toString())
            val resp = responseDTO.blockingGet().statusMessage
            val code = responseDTO.blockingGet().statusCode
            val data = responseDTO.blockingGet().data
        } catch (e: Exception){
            Log.e(TAG,"Got Error"+e.message)
            Log.wtf(TAG,e)

        }

    }
}