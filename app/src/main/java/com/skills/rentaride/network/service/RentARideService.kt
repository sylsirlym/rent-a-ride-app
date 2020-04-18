package com.skills.rentaride.network.service


import com.skills.rentaride.di.DaggerApiComponent
import com.skills.rentaride.model.ResponseDTO
import com.skills.rentaride.network.RentARideAPI
import dagger.android.DaggerActivity
import io.reactivex.Single
import javax.inject.Inject


class RentARideService {

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
        return api.getLendTransactionHistory(msisdn)
    }
}