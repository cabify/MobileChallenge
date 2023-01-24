package com.cabify.shared.product.domain.repository

import com.cabify.shared.product.domain.entities.ProductEntity
import io.reactivex.rxjava3.core.Single

interface ProductsRepository {
    fun getProducts(): Single<List<ProductEntity>>
}