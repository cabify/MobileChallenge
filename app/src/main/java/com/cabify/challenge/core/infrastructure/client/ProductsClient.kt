package com.cabify.challenge.core.infrastructure.client

import com.cabify.challenge.core.domain.products.Products

interface ProductsClient {
    fun getProducts(): Products
}