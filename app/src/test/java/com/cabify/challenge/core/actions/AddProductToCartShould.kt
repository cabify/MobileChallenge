package com.cabify.challenge.core.actions

import com.cabify.challenge.builder.ProductBuilder
import com.cabify.challenge.core.domain.products.Product
import com.cabify.challenge.core.infrastructure.repositories.CartRepository
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class AddProductToCartShould {

    private lateinit var addProductToCart: AddProductToCart
    private lateinit var cartRepository: CartRepository

    @Before
    fun setUp() {
        cartRepository = mockk(relaxed = true)
    }

    @Test
    fun `add to product to chart`() {
        givenAnAddProductToCart()
        val product = ProductBuilder().mug().build()

        whenAddCartToProduct(product)

        thenAddProductToCart(product)

    }

    private fun thenAddProductToCart(product: Product) {
        verify { cartRepository.save(product) }
    }

    private fun whenAddCartToProduct(product: Product) {
        addProductToCart(product)
    }

    private fun givenAnAddProductToCart() {
        addProductToCart = AddProductToCart(cartRepository)
    }
}