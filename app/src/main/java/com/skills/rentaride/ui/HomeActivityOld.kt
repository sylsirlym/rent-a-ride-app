//package com.skills.rentaride.ui
//
//import android.os.Bundle
//import android.util.Log
//import android.widget.EditText
//import androidx.appcompat.app.AppCompatActivity
//import androidx.databinding.DataBindingUtil
//import com.google.gson.Gson
//import com.skills.rentaride.R
//import com.skills.rentaride.databinding.FragmentHomeBinding
//import com.skills.rentaride.model.ProfileDTO
//
//class HomeActivityOld : AppCompatActivity(){
//    private val TAG = "HomeActivity"
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
////        setContentView(R.layout.fragment_home)
//        val binding: FragmentHomeBinding = DataBindingUtil.setContentView(
//            this, R.layout.fragment_home)
//
//        try {
//            val profile = intent.getStringExtra("profileDetails")
//            Log.i(TAG, profile)
//            val gson = Gson()
//            val profileDetails = gson.fromJson(profile, ProfileDTO::class.java)
//            Log.i(TAG, profileDetails.fname)
//            binding.profile=profileDetails;
//        } catch (e: Exception){
//            Log.e(TAG,"Got Error"+e.message)
//            Log.wtf(TAG,e)
//
//        }
//
//    }
//}