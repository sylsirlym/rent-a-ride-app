package com.skills.rentaride.network

import com.skills.rentaride.model.ResponseDTO
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface RentARideAPI {

    @GET("https://e94be236.ngrok.io/mocks/profile/{msisdn}/pp.php")
    fun getProfile(@Path("msisdn") msisdn: String): Single<ResponseDTO>
}