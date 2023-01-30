package com.cabify.challenge.infrastructure.repositories

import com.cabify.challenge.core.domain.products.Products
import com.cabify.challenge.core.infrastructure.repositories.ProductsRepository

open class InMemoryItemsRepository(private var products: Products? = null) :
    ProductsRepository {

    override fun get(): Products? {
        return products
    }

    override fun save(products: Products) {
        this.products = products
    }
}