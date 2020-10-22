package com.skills.rentaride.di

import com.skills.rentaride.network.service.RentARideService
import com.skills.rentaride.ui.*
import dagger.Component
import com.skills.rentaride.viewmodel.ListViewModel

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(splashActivity: SplashActivity)
    fun inject(service: RentARideService)
    fun inject(mainActivity: MainActivity)
    fun inject(homeActivity: HomeActivity)
    fun inject(registerActivity: RegisterActivity)
    fun inject(rentActivity: RentActivity)
    fun inject(viewModel: ListViewModel)

}