package com.skills.rentaride.di

import com.skills.rentaride.network.service.RentARideService
import com.skills.rentaride.ui.HomeActivity
import com.skills.rentaride.ui.MainActivity
import com.skills.rentaride.ui.SplashActivity
import dagger.Component
import com.skills.rentaride.viewmodel.ListViewModel

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(splashActivity: SplashActivity)
    fun inject(service: RentARideService)
    fun inject(mainActivity: MainActivity)
    fun inject(homeActivity: HomeActivity)
    fun inject(viewModel: ListViewModel)

}