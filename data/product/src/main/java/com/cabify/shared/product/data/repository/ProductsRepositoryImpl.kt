package com.cabify.shared.product.data.repository

import com.cabify.shared.product.data.datasource.ProductsNetworkDataSource
import com.cabify.shared.product.domain.entities.ProductEntity
import com.cabify.shared.product.domain.repository.ProductsRepository
import io.reactivex.rxjava3.core.Single

internal class ProductsRepositoryImpl(private val productsNetworkDataSource: ProductsNetworkDataSource) :
    ProductsRepository {
    override fun getProducts(): Single<List<ProductEntity>> =
        productsNetworkDataSource.getProducts()
}