package com.cabify.mobilechallenge.core.base.di

import com.cabify.library.utils.provider.StringsProvider
import com.cabify.library.utils.provider.StringsProviderImpl
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val OBSERVE_SCHEDULER = named("observe_scheduler")
val SUBSCRIBE_SCHEDULER = named("subscribe_scheduler")

val coreBaseModule = module {
    factory(OBSERVE_SCHEDULER) { AndroidSchedulers.mainThread() }
    factory(SUBSCRIBE_SCHEDULER) { Schedulers.io() }
    single<StringsProvider> { StringsProviderImpl(androidContext()) }
}