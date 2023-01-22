package com.cabify.core.network.di

import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private val DEFAULT_HTTP_CLIENT = named("DEFAULT_HTTP_CLIENT")
private val DEFAULT_RETROFIT = named("DEFAULT_RETROFIT")

val networkModule = module {
    single(DEFAULT_HTTP_CLIENT) {
        buildHttpClient()
    }
    single(DEFAULT_RETROFIT) {
        buildRetrofit(get(DEFAULT_HTTP_CLIENT))
    }
}


private const val CONNECT_TIMEOUT = 10L

private fun buildHttpClient(
): OkHttpClient {
    return OkHttpClient.Builder()
        .cache(null)
        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()
}

private fun buildRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
    .client(okHttpClient)
    .build()

