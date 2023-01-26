package com.cabify.challenge.core.domain.services

import com.cabify.challenge.core.domain.products.Products

interface PromosService {
    fun apply(products: Products)
}