package com.cabify.demo.data.repository

import com.cabify.demo.data.model.ResponseApi
import com.cabify.demo.data.service.ProductApiService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductRepositoryImpl : ProductRepository {
    private val remoteDataSource: ProductApiService = ProductApiService.getInstance()
    private val refreshIntervalMs: Long = 5000

    override fun getProducts(): Flow<ResponseApi> = flow {
        // Suspends the coroutine for some time
        // add delay to show skeleton
        delay(refreshIntervalMs)
        val latestNews = remoteDataSource.getProducts()
        emit(latestNews)
    }
}