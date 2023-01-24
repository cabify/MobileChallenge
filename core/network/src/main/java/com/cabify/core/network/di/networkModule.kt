package com.cabify.core.network.di

import com.cabify.core.network.service.ServiceBuilder
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module

private val DEFAULT_HTTP_CLIENT = named("DEFAULT_HTTP_CLIENT")

const val BASE_URL_PROPERTY = "BASE_URL_PROPERTY"

val networkModule = module {
    single(DEFAULT_HTTP_CLIENT) {
        buildHttpClient()
    }
    single { ServiceBuilder(baseUrl = getProperty(BASE_URL_PROPERTY), get(DEFAULT_HTTP_CLIENT)) }
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
