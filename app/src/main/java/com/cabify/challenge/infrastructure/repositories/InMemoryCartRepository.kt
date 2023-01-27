package com.cabify.challenge.infrastructure.repositories

import com.cabify.challenge.core.domain.products.Product
import com.cabify.challenge.core.domain.products.Products
import com.cabify.challenge.core.infrastructure.repositories.CartRepository

class InMemoryCartRepository(private var cart: MutableList<Product> = mutableListOf()) :
    CartRepository {

    override fun get(): Products {
        return Products(cart)
    }

    override fun save(product: Product) {
        cart.add(product)
    }
}