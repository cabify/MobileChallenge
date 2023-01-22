package com.cabify.mobilechallenge.app

import android.app.Application
import com.cabify.core.network.di.networkModule
import com.cabify.mobilechallenge.app.di.appModule
import com.cabify.mobilechallenge.features.cart.di.cartModule
import com.cabify.mobilechallenge.features.home.di.homeModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.fileProperties

class MobileChallengeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initDependencyInjection()
    }

    private fun initDependencyInjection() {
        startKoin {
            androidLogger()
            androidContext(this@MobileChallengeApplication)
            modules(
                appModule,
                cartModule,
                homeModule,
                networkModule
            )
            fileProperties()
        }
    }
}