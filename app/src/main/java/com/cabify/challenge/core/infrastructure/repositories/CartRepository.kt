package com.cabify.challenge.core.infrastructure.repositories

import com.cabify.challenge.core.domain.products.Product
import com.cabify.challenge.core.domain.products.Products

interface CartRepository {
    fun get(): Products
    fun save(product: Product)
}