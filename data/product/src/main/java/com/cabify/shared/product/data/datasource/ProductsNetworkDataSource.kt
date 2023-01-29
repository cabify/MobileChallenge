package com.cabify.shared.product.data.datasource

import com.cabify.shared.product.domain.entities.ProductEntity
import io.reactivex.rxjava3.core.Single

internal interface ProductsNetworkDataSource {
    fun getProducts(): Single<List<ProductEntity>>
}