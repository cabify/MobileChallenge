package com.cabify.challenge.infrastructure.factory

import com.cabify.challenge.infrastructure.client.products.api.APIClient
import com.cabify.challenge.infrastructure.client.products.api.RetrofitProductsClient
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ClientFactory {

    val gson = GsonBuilder().setLenient().create()
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://gist.githubusercontent.com/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()


    private val apiClient = retrofit.create(APIClient::class.java)

    val retrofitClient = RetrofitProductsClient(apiClient)


}
