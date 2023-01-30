package com.cabify.challenge.core.domain.products

import com.cabify.challenge.builder.ProductBuilder
import org.junit.Assert.assertEquals
import org.junit.Test

class ProductsShould {

    @Test
    fun `get total price from one MUG`() {
        val mug = ProductBuilder().mug().build()
        givenProductsWith(listOf(mug))

        whenGetTotalPrice()

        thenThePriceIs(sevenAndAHalfEur)
    }

    @Test
    fun `get total price from one TSHIRT`() {
        val tshirt = ProductBuilder().tshirt().build()
        givenProductsWith(listOf(tshirt))

        whenGetTotalPrice()

        thenThePriceIs(twentyEur)
    }

    @Test
    fun `get total price from one VOUCHER`() {
        val voucher = ProductBuilder().voucher().build()
        givenProductsWith(listOf(voucher))

        whenGetTotalPrice()

        thenThePriceIs(fiveEur)
    }


    @Test
    fun `get total price 0 when there is no product`() {
        givenProductsWith(emptyList())

        whenGetTotalPrice()

        thenThePriceIs(zeroEur)
    }

    @Test
    fun `get total price 32,5 eur when there is one of each product`() {
        val mug = ProductBuilder().mug().build()
        val voucher = ProductBuilder().voucher().build()
        val tshirt = ProductBuilder().tshirt().build()
        givenProductsWith(listOf(mug, voucher, tshirt))

        whenGetTotalPrice()

        thenThePriceIs(thirtyTwoAndAHalfEur)
    }

    @Test
    fun `get mug by code`() {
        val mug = ProductBuilder().mug().build()
        val voucher = ProductBuilder().voucher().build()
        val tshirt = ProductBuilder().tshirt().build()
        givenProductsWith(listOf(mug, voucher, tshirt))

        whenRetrieveProductsByCode(Code.MUG)

        thenTheProductsAre(mugs)
    }

    @Test
    fun `get voucher by code`() {
        val mug = ProductBuilder().mug().build()
        val voucher = ProductBuilder().voucher().build()
        val tshirt = ProductBuilder().tshirt().build()
        givenProductsWith(listOf(mug, voucher, tshirt))

        whenRetrieveProductsByCode(Code.VOUCHER)

        thenTheProductsAre(vouchers)
    }

    @Test
    fun `get empty products when code not matchs`() {
        givenProductsWith(emptyList())

        whenRetrieveProductsByCode(Code.VOUCHER)

        thenTheProductsAre(emptyList())
    }
    @Test
    fun `get all products`() {
        val mug = ProductBuilder().mug().build()
        val voucher = ProductBuilder().voucher().build()
        val tshirt = ProductBuilder().tshirt().build()
        givenProductsWith(listOf(mug, voucher, tshirt))

        filteredProducts = products.getAllProducts()

        thenTheProductsAre(listOf(mug, voucher, tshirt))
    }

    private fun givenProductsWith(someProducts: List<Product>) {
        products = Products(someProducts)
    }

    private fun whenRetrieveProductsByCode(code: Code) {
        filteredProducts = products.getProductsByCode(code)
    }

    private fun whenGetTotalPrice() {
        totalPrice = products.totalPrice()
    }


    private fun thenThePriceIs(expected: Price) {
        assertEquals(expected, totalPrice)
    }

    private fun thenTheProductsAre(expected: List<Product>) {
        assertEquals(expected, filteredProducts)
    }

    companion object {
        private lateinit var totalPrice: Price
        private lateinit var products: Products
        private lateinit var filteredProducts: List<Product>
        private var mugs = listOf(ProductBuilder().mug().build())
        private var vouchers = listOf(ProductBuilder().voucher().build())
        private val fiveEur = Price.eurPrice(amount = 5.0)
        private val sevenAndAHalfEur = Price.eurPrice(amount = 7.5)
        private val twentyEur = Price.eurPrice(amount = 20.0)
        private val zeroEur = Price.eurPrice(amount = 0.0)
        private val thirtyTwoAndAHalfEur = Price.eurPrice(amount = 32.5)

    }
}