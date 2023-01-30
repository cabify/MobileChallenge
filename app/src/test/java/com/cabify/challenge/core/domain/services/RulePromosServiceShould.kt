package com.cabify.challenge.core.domain.services

import com.cabify.challenge.builder.ProductBuilder
import com.cabify.challenge.builder.ProductOrderBuilder
import com.cabify.challenge.core.domain.cart.Cart
import com.cabify.challenge.core.domain.products.Products
import com.cabify.challenge.core.domain.promos.PromoRule
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class RulePromosServiceShould {


    private lateinit var rule: PromoRule
    private lateinit var rules: List<PromoRule>
    private lateinit var promosService: PromosService

    @Before
    fun setUp() {
        rule = mockk()
        rules = listOf(rule)
        promosService = RulePromosService(rules)
    }

    @Test
    fun `return products without applied promos`() {
        givenAPromoService()

        whenApplyPromos()

        thenThePromoIsApplied()
    }


    private fun thenThePromoIsApplied() {
        assertEquals(productsPromos, cartResult)
    }

    private fun whenApplyPromos() {
        cartResult = promosService.apply(products)
    }

    private fun givenAPromoService() {
        every { rule.apply(products) } returns productsOrderRule
    }

    companion object {
        private lateinit var cartResult: Cart


        private val mug = ProductBuilder().mug().build()
        private val tshirt = ProductBuilder().tshirt().build()
        private val voucher = ProductBuilder().voucher().build()

        private val productsOrderRule = ProductOrderBuilder().mug().build()

        private val products = Products(listOf(mug, tshirt, voucher))
        private val productsPromos = Cart(products, listOf(productsOrderRule))

    }
}