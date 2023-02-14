package com.cabify.demo.data.repository

import com.cabify.demo.data.model.ResponseApi
import com.cabify.demo.data.service.ProductLocalDataSource
import com.cabify.demo.data.service.ProductLocalDataSourceImpl
import com.cabify.demo.data.service.ProductRemoteDataSource
import com.cabify.demo.data.service.ProductRemoteDataSourceImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductRepositoryImpl : ProductRepository {
    private val remoteDataSource: ProductRemoteDataSource = ProductRemoteDataSourceImpl()
    private val localDataSource: ProductLocalDataSource = ProductLocalDataSourceImpl()
    private val refreshIntervalMs: Long = 5000

    override fun getProducts(): Flow<ResponseApi> = flow {
        val latestProducts = remoteDataSource.getProducts()
        emit(latestProducts)
    }

    override fun getLocalProducts(): ResponseApi = localDataSource.getProducts()


}