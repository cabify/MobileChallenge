package com.cabify.challenge.core.actions

import com.cabify.challenge.core.domain.products.Products
import com.cabify.challenge.core.infrastructure.repositories.CartRepository

class GetProductsFromCart(private val cartRepository: CartRepository) {
    operator fun invoke(): Products {
        return cartRepository.get()
    }
}