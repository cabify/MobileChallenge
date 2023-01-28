package com.cabify.mobilechallenge.app

import android.app.Application
import com.cabify.core.network.di.BASE_URL_PROPERTY
import com.cabify.core.network.di.networkModule
import com.cabify.mobilechallenge.BuildConfig
import com.cabify.mobilechallenge.app.di.appModule
import com.cabify.mobilechallenge.cart.di.cartSharedModule
import com.cabify.mobilechallenge.core.base.di.coreBaseModule
import com.cabify.mobilechallenge.features.cart.di.cartModule
import com.cabify.mobilechallenge.features.home.di.homeModule
import com.cabify.mobilechallenge.persistence.di.corePersistenceModule
import com.cabify.shared.product.di.coreProductModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

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
                coreBaseModule,
                coreProductModule,
                corePersistenceModule,
                networkModule,
                cartSharedModule
            )
            properties(hashMapOf(BASE_URL_PROPERTY to BuildConfig.BASE_URL))
        }
    }
}