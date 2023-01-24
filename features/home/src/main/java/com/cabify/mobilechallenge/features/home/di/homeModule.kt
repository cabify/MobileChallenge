package com.cabify.mobilechallenge.features.home.di

import com.cabify.library.utils.CurrencyUtils
import com.cabify.mobilechallenge.core.base.di.OBSERVE_SCHEDULER
import com.cabify.mobilechallenge.core.base.di.SUBSCRIBE_SCHEDULER
import com.cabify.mobilechallenge.features.home.presentation.mapper.ProductsPromotionsDomainToPresentationMapper
import com.cabify.mobilechallenge.features.home.presentation.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    viewModel {
        HomeViewModel(
            get(),
            get(),
            get(),
            get(),
            subscribeScheduler = get(SUBSCRIBE_SCHEDULER),
            observerScheduler = get(OBSERVE_SCHEDULER)
        )
    }
    single { CurrencyUtils() }
    single { ProductsPromotionsDomainToPresentationMapper(get()) }
}