package com.skills.rentaride.network

import com.skills.rentaride.model.ResponseDTO
import com.skills.rentaride.model.Responses
import com.skills.rentaride.model.UserDTO
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RentARideAPI {

    @GET("customer/{msisdn}")
    fun getProfile(@Path("msisdn") msisdn: String): Single<ResponseDTO>

    @GET("lending/lend-history/{msisdn}")
    fun getLendTransactionHistory(@Path("msisdn") msisdn: String): Single<ResponseDTO>

    @POST("customer/create")
    fun createUser(@Body request: RequestBody): Call<ResponseDTO>
}