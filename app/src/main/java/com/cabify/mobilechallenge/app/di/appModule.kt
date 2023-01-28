package com.cabify.mobilechallenge.app.di

import com.cabify.mobilechallenge.app.presentation.viewmodel.MainViewModel
import com.cabify.mobilechallenge.core.base.di.OBSERVE_SCHEDULER
import com.cabify.mobilechallenge.core.base.di.SUBSCRIBE_SCHEDULER
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel {
        MainViewModel(
            get(),
            subscribeScheduler = get(SUBSCRIBE_SCHEDULER),
            observerScheduler = get(OBSERVE_SCHEDULER)
        )
    }
}