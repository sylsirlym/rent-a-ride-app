package com.skills.rentaride.network.service


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import com.skills.rentaride.di.DaggerApiComponent
import com.skills.rentaride.model.ResponseDTO
import com.skills.rentaride.model.UserDTO
import com.skills.rentaride.network.RentARideAPI
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.Call
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
        return api.getProfile(msisdn)
    }

    fun getLendTransactionHistory(msisdn: String): Single<ResponseDTO> {
        val responseDTO: Single<ResponseDTO> = api.getLendTransactionHistory(msisdn)
        Log.i(TAG, "Inside getLendTransactionHistory Now ##################################################################")
        Log.i(TAG, "Inside getLendTransactionHistory"+responseDTO.blockingGet().statusMessage)
        Log.i(TAG, "Inside getLendTransactionHistory"+responseDTO.blockingGet().data.toString())
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