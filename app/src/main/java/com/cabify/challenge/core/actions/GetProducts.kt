package com.cabify.challenge.core.actions

import com.cabify.challenge.core.domain.products.Products
import com.cabify.challenge.core.infrastructure.client.ProductsClient
import com.cabify.challenge.core.infrastructure.repositories.ProductsRepository

class GetProducts(
    private val productsRepository: ProductsRepository,
    private val productsClient: ProductsClient
) {
    suspend operator fun invoke(): Products {
        val storedProducts = productsRepository.get()
        return storedProducts ?: findAndReturnProducts()
    }

    private suspend fun findAndReturnProducts(): Products {
        val products = productsClient.getProducts()
        productsRepository.save(products)
        return products
    }
}