package com.skills.rentaride.di

import com.skills.rentaride.network.RentARideAPI
import com.skills.rentaride.network.service.RentARideService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {
    private val BASE_URL = "https://raw.githubusercontent.com"

    @Provides
    fun provideCountiesApi(): RentARideAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(RentARideAPI::class.java)
    }

    @Provides
    fun provideRentARideService(): RentARideService = RentARideService()

}