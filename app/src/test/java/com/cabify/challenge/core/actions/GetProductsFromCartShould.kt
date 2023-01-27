package com.cabify.challenge.core.actions

import com.cabify.challenge.builder.ProductBuilder
import com.cabify.challenge.core.domain.products.Products
import com.cabify.challenge.core.infrastructure.repositories.CartRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
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
        givenGetProductsFromCart()
        givenSomeProducts()

        whenGetProductsFromCart()

        thenRetrieveProducts()
    }

    @Test
    fun `get empty products from chart`() {
        givenGetProductsFromCart()
        givenNoProducts()

        whenGetProductsFromCart()

        thenReturnNoProducts()
    }

    private fun thenReturnNoProducts() {
        assertEquals(productsFromCart, Products(emptyList()))
    }

    private fun givenGetProductsFromCart() {
        getProductsFromCart = GetProductsFromCart(cartRepository)
    }

    private fun givenSomeProducts() {
        every { cartRepository.get() } returns products
    }

    private fun givenNoProducts() {
        every { cartRepository.get() } returns Products(emptyList())
    }

    private fun whenGetProductsFromCart() {
        productsFromCart = getProductsFromCart()
    }

    private fun thenRetrieveProducts() {
        assertEquals(productsFromCart, products)
    }



    companion object {
        private val mug = ProductBuilder().mug().build()
        private val voucher = ProductBuilder().voucher().build()
        private val products = Products(listOf(mug, voucher))

        private lateinit var productsFromCart: Products
    }
}