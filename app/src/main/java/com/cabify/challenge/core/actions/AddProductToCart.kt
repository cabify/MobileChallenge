package com.cabify.challenge.core.actions

import com.cabify.challenge.core.domain.products.Product
import com.cabify.challenge.core.infrastructure.repositories.CartRepository

class AddProductToCart(private val cartRepository: CartRepository) {

    operator fun invoke(product: Product) {
        cartRepository.save(product)
    }
}