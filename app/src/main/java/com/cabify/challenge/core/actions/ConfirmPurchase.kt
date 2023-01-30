package com.cabify.challenge.core.actions

import com.cabify.challenge.core.infrastructure.repositories.CartRepository

class ConfirmPurchase(private val cartRepository: CartRepository) {

    operator fun invoke() {
        cartRepository.clear()
    }
}