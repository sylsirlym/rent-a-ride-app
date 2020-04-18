package com.skills.rentaride.network

import com.skills.rentaride.model.ResponseDTO
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface RentARideAPI {

    @GET("customer/{msisdn}")
    fun getProfile(@Path("msisdn") msisdn: String): Single<ResponseDTO>

    @GET("lending/lend-history/{msisdn}")
    fun getLendTransactionHistory(@Path("msisdn") msisdn: String): Single<ResponseDTO>
}