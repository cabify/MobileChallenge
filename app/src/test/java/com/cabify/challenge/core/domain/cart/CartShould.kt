package com.cabify.challenge.core.domain.cart

import com.cabify.challenge.builder.ProductBuilder
import com.cabify.challenge.builder.ProductOrderBuilder
import com.cabify.challenge.core.domain.products.Code
import com.cabify.challenge.core.domain.products.Price
import com.cabify.challenge.core.domain.products.Products
import org.junit.Assert.assertEquals
import org.junit.Test

class CartShould {

    @Test
    fun `return a product order from cart`() {
        givenACart()

        whenGetProductsOrder()

        thenGetProductOrder()
    }

    @Test
    fun `return total no promo amount`() {
        givenACart()

        whenGetTotalAmountNoPromo()

        thenGetTotalAmoutNoPromo()
    }


    @Test
    fun `return total promo amount`() {
        givenACartWithPromo()

        whenGetTotalAmountPromo()

        thenGetTotalAmountPromo()
    }

    @Test
    fun `return quantity of items using code`() {
        cart = Cart(productsToCount, productsOrderWithPromo)

        itemsQuantityResult = cart.getItemsQuantityByCode(Code.MUG)

        assertEquals(itemsQuantityResult, itemsQuantity)
    }

    private fun givenACart() {
        cart = Cart(noPromoProducts, productsOrder)
    }

    private fun givenACartWithPromo() {
        cart = Cart(noPromoProducts, productsOrderWithPromo)
    }

    private fun whenGetTotalAmountNoPromo() {
        totalAmountNoPromoResult = cart.totalAmountNoPromo()
    }

    private fun whenGetProductsOrder() {
        productOrderResult = cart.getProductsOrder()
    }

    private fun whenGetTotalAmountPromo() {
        totalAmountPromoResult = cart.totalAmountPromo()
    }

    private fun thenGetTotalAmoutNoPromo() {
        assertEquals(totalAmountNoPromoResult, totalAmountNoPromo)
    }

    private fun thenGetTotalAmountPromo() {
        assertEquals(totalAmountPromoResult, totalAmountPromo)
    }

    private fun thenGetProductOrder() {
        assertEquals(productOrderResult, productsOrder)
    }

    companion object {
        private lateinit var productOrderResult: List<ProductOrder>
        private lateinit var totalAmountNoPromoResult: Price
        private lateinit var totalAmountPromoResult: Price
        private lateinit var cart: Cart
        private var itemsQuantityResult: Int = 0
        private var itemsQuantity: Int = 4

        private val mug = ProductBuilder().mug().build()
        private val tshirt = ProductBuilder().tshirt().build()
        private val voucher = ProductBuilder().voucher().build()

        private val mugOrderWithPromo = ProductOrderBuilder().mug().build().copy(price = Price(5.0))
        private val mugOrder = ProductOrderBuilder().mug().build()
        private val tshirtOrder = ProductOrderBuilder().tshirt().build()
        private val voucherOrder = ProductOrderBuilder().voucher().build()

        private val productsOrder = listOf(mugOrder, tshirtOrder, voucherOrder)
        private val productsOrderWithPromo = listOf(mugOrderWithPromo, tshirtOrder, voucherOrder)
        private val noPromoProducts = Products(listOf(mug, tshirt, voucher))
        private val productsToCount = Products(listOf(mug, mug, mug, mug, tshirt, voucher))

        private val totalAmountNoPromo = Price(32.5)
        private val totalAmountPromo = Price(30.0)
    }
}