package com.cabify.shared.product.data.datasource

import com.cabify.mobilechallenge.core.base.mapper.Mapper
import com.cabify.shared.product.data.mapper.GetProductResponseToDomainMapper
import com.cabify.shared.product.data.model.GetProductsResponse
import com.cabify.shared.product.data.service.ProductsService
import com.cabify.shared.product.domain.entities.ProductEntity
import io.reactivex.rxjava3.core.Single

internal class ProductsNetworkDataSourceImpl(
    private val productsService: ProductsService,
    private val getProductResponseToDomainMapper: Mapper<GetProductsResponse, List<ProductEntity>>
) :
    ProductsNetworkDataSource {
    override fun getProducts(): Single<List<ProductEntity>> =
        productsService.getProducts().map(getProductResponseToDomainMapper::map)
}