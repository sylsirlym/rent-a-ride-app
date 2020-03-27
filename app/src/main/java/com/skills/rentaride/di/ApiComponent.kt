package com.skills.rentaride.di

import com.skills.rentaride.network.service.RentARideService
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(service: RentARideService)

}