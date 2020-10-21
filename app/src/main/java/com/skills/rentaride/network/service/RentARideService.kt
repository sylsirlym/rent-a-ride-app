package com.skills.rentaride.network.service


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import com.google.gson.Gson
import com.skills.rentaride.di.DaggerApiComponent
import com.skills.rentaride.model.ResponseDTO
import com.skills.rentaride.model.UserDTO
import com.skills.rentaride.network.RentARideAPI
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.Call
import java.net.URL
import javax.inject.Inject


class RentARideService {
    private val TAG = "Service"
    @Inject
    lateinit var  api: RentARideAPI

    init {
        DaggerApiComponent.create()
            .inject(this)
    }

    /**
     * This function implements the service and gets the user profile.
     */
    fun getProfile(msisdn: String): Single<ResponseDTO> {
        Log.d(TAG, "Inside getProfile. MSISDN:$msisdn")
        val responseDTO =api.getProfile(msisdn)
        val outputJson: String = Gson().toJson(responseDTO)
        Log.d(TAG, "Inside getLendTransactionHistory:$outputJson")
        return responseDTO
    }

    fun getLendTransactionHistory(msisdn: String): Single<ResponseDTO> {
        Log.d(TAG, "Inside getLendTransactionHistory. MSISDN:$msisdn")
        val responseDTO = api.getLendTransactionHistory(msisdn)
        Log.d(TAG, "Inside getLendTransactionHistory Now ##################################################################")
        Log.d(TAG, "Inside getLendTransactionHistory:"+responseDTO.blockingGet().data.toString())
        return responseDTO
    }

    fun createProfile(requestBody: RequestBody): Call<ResponseDTO> {
        Log.i(TAG, "Inside createProfile ############################################# $requestBody")
        return api.createUser(requestBody)
    }

    fun isNetworkAvailable(context: Context) : Boolean {
        Log.i(TAG, "Inside isNetworkAvailable ##################################################################")
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true;
    }
}