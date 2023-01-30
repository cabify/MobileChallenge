package com.cabify.challenge.core.actions

import com.cabify.challenge.core.infrastructure.repositories.CartRepository
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class ConfirmPurchaseShould {


    private lateinit var confirmPurchase: ConfirmPurchase
    private lateinit var cartRepository: CartRepository

    @Before
    fun setUp() {
        cartRepository = mockk(relaxed = true)
        confirmPurchase = ConfirmPurchase(cartRepository)
    }

    @Test
    fun `clear cart`() {

        whenConfirmPurchase()

        thenClearCart()
    }


    private fun whenConfirmPurchase() {
        confirmPurchase.invoke()
    }

    private fun thenClearCart() {
        verify { cartRepository.clear() }
    }

}