package com.cabify.shared.product.data.datasource

import com.cabify.shared.product.data.mapper.GetProductResponseToDomainMapper
import com.cabify.shared.product.data.service.ProductsService
import com.cabify.shared.product.domain.entities.ProductEntity
import io.reactivex.rxjava3.core.Single

internal class ProductsNetworkDataSourceImpl(
    private val productsService: ProductsService,
    private val getProductResponseToDomainMapper: GetProductResponseToDomainMapper
) :
    ProductsNetworkDataSource {
    override fun getProducts(): Single<List<ProductEntity>> =
        productsService.getProducts().map(getProductResponseToDomainMapper::map)
}