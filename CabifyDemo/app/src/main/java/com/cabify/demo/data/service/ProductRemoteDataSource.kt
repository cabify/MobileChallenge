package com.cabify.demo.data.service

import com.cabify.demo.data.model.ResponseApi
import retrofit2.http.GET

interface ProductRemoteDataSource {

    @GET("palcalde/6c19259bd32dd6aafa327fa557859c2f/raw/ba51779474a150ee4367cda4f4ffacdcca479887/Products.json")
    suspend fun getProducts(): ResponseApi
}