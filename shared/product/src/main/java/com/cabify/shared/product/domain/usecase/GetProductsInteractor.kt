package com.cabify.shared.product.domain.usecase

import com.cabify.shared.product.domain.entities.ProductEntity
import com.cabify.shared.product.domain.repository.ProductsRepository
import io.reactivex.rxjava3.core.Single

internal class GetProductsInteractor(
    private val productRepository: ProductsRepository
) :
    GetProductsUseCase {
    override fun invoke(): Single<List<ProductEntity>> = productRepository.getProducts()
}