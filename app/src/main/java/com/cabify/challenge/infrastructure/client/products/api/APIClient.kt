package com.cabify.challenge.infrastructure.client.products.api

import com.cabify.challenge.infrastructure.client.products.dto.ProductsDTO
import retrofit2.Response
import retrofit2.http.GET

interface APIClient {

    @GET("palcalde/6c19259bd32dd6aafa327fa557859c2f/raw/ba51779474a150ee4367cda4f4ffacdcca479887/Products.json")
    suspend fun getProducts(): Response<ProductsDTO>
}