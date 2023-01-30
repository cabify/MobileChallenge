package com.cabify.challenge.core.actions

import com.cabify.challenge.core.domain.cart.Cart
import com.cabify.challenge.core.domain.services.PromosService
import com.cabify.challenge.core.infrastructure.repositories.CartRepository

class GetProductsFromCart(
    private val cartRepository: CartRepository,
    private val promosService: PromosService
) {
    operator fun invoke(): Cart {
        val products = cartRepository.get()
        val cart = promosService.apply(products)
        val filteredProductsOrder = cart.getProductsOrder().filter { it.price().amount() != 0.0 }

        return cart.copy(productsOrder = filteredProductsOrder)
    }
}