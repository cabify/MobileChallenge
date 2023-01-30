package com.cabify.challenge.core.actions

import com.cabify.challenge.builder.ProductBuilder
import com.cabify.challenge.builder.ProductOrderBuilder
import com.cabify.challenge.core.domain.cart.Cart
import com.cabify.challenge.core.domain.products.Price
import com.cabify.challenge.core.domain.products.Products
import com.cabify.challenge.core.domain.services.PromosService
import com.cabify.challenge.core.infrastructure.repositories.CartRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetProductsFromCartShould {


    private lateinit var getProductsFromCart: GetProductsFromCart
    private lateinit var cartRepository: CartRepository
    private lateinit var promosService: PromosService

    @Before
    fun setUp() {
        cartRepository = mockk()
        promosService = mockk()
    }


    @Test
    fun `apply promos to cart`() {
        givenGetProductsFromCart()
        givenSomeProducts()
        givenAPromosService()

        whenGetProductsFromCart()

        thenRetrieveCart()
    }

    @Test
    fun `remove products with no price from cart`() {
        givenGetProductsFromCart()
        givenSomeProducts()
        every { promosService.apply(products) } returns cartNoFiltered

        whenGetProductsFromCart()

        assertEquals(cartResult, cartFiltered)
    }

    private fun givenAPromosService() {
        every { promosService.apply(products) } returns cart
    }

    private fun givenGetProductsFromCart() {
        getProductsFromCart = GetProductsFromCart(cartRepository, promosService)
    }

    private fun givenSomeProducts() {
        every { cartRepository.get() } returns products
    }

    private fun whenGetProductsFromCart() {
        cartResult = getProductsFromCart()
    }

    private fun thenRetrieveCart() {
        assertEquals(cartResult, cart)
    }


    companion object {
        private val mug = ProductBuilder().mug().build()
        private val voucher = ProductBuilder().voucher().build()

        private val products = Products(listOf(mug, voucher))


        private val mugOrder = ProductOrderBuilder().mug().build()
        private val voucherOrder = ProductOrderBuilder().voucher().build()
        private val tshirtOrder = ProductOrderBuilder().tshirt().build()
        private val voucherNoPriceOrder =
            ProductOrderBuilder().voucher().build().copy(price = Price.eurPrice(0.0))
        private val productsWithNoPrice =
            listOf(mugOrder, voucherOrder, tshirtOrder, voucherNoPriceOrder)
        private val productsFiltered = listOf(mugOrder, voucherOrder, tshirtOrder)

        private val cart = Cart(products, emptyList())
        private val cartFiltered = Cart(products, productsFiltered)
        private val cartNoFiltered = Cart(products, productsWithNoPrice)

        private lateinit var cartResult: Cart
    }
}