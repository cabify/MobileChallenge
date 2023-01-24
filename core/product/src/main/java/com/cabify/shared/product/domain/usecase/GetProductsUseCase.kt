package com.cabify.shared.product.domain.usecase

import com.cabify.shared.product.domain.entities.ProductEntity
import io.reactivex.rxjava3.core.Single

interface GetProductsUseCase {
    operator fun invoke(): Single<List<ProductEntity>>
}