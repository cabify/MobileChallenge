package com.cabify.challenge.core.infrastructure.repositories

import com.cabify.challenge.core.domain.products.Products

interface ProductsRepository {
    fun get(): Products?
    fun save(products: Products)
}