package com.cabify.challenge.core.actions

import com.cabify.challenge.builder.ProductBuilder
import com.cabify.challenge.core.domain.products.Products
import com.cabify.challenge.core.infrastructure.repositories.CartRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetProductsFromCartShould {


    private lateinit var getProductsFromCart: GetProductsFromCart
    private lateinit var cartRepository: CartRepository

    @Before
    fun setUp() {
        cartRepository = mockk()
    }

    @Test
    fun `get products from chart`() {
        val mug = ProductBuilder().mug().build()
        val voucher = ProductBuilder().voucher().build()
        val products = Products(listOf(mug, voucher))
        getProductsFromCart = GetProductsFromCart(cartRepository)
        every { cartRepository.get() } returns products

        val productsFromCart = getProductsFromCart()

        assertEquals(productsFromCart, products)
    }
}