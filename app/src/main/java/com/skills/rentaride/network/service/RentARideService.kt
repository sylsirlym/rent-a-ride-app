package com.skills.rentaride.network.service


import android.util.Log
import com.skills.rentaride.di.DaggerApiComponent
import com.skills.rentaride.model.LendTransactionDTO
import com.skills.rentaride.model.ResponseDTO
import com.skills.rentaride.model.Responses
import com.skills.rentaride.network.RentARideAPI
import io.reactivex.Observable
import io.reactivex.Single
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
     * This function implements the service and gets the countries.
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
}