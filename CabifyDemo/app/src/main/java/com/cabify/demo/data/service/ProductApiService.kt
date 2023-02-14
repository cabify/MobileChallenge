package com.cabify.demo.data.service

import com.cabify.demo.BuildConfig.BASE_URL
import com.cabify.demo.data.model.ResponseApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ProductApiService {

    @GET("palcalde/6c19259bd32dd6aafa327fa557859c2f/raw/ba51779474a150ee4367cda4f4ffacdcca479887/Products.json")
    suspend fun getProducts(): ResponseApi

    companion object {
        private var productApiService: ProductApiService? = null

        fun getInstance(): ProductApiService {
            if (productApiService == null) {
                productApiService = Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build()
                    .create(ProductApiService::class.java)
            }

            return productApiService as ProductApiService
        }
    }
}

