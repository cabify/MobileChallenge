package com.cabify.challenge.core.domain.promos

import com.cabify.challenge.builder.ProductBuilder
import com.cabify.challenge.builder.ProductOrderBuilder
import com.cabify.challenge.core.domain.products.Price
import com.cabify.challenge.core.domain.products.Products
import org.junit.Assert.assertEquals
import org.junit.Test

class MugNoPromoRuleShould {
    @Test
    fun `not apply promo`() {
        val rule = MugNoPromoRule()

        val productOrder = rule.apply(noMugProducts)

        assertEquals(noPriceProduct, productOrder)
    }


    @Test
    fun `no promo for mug`() {
        val rule = MugNoPromoRule()

        val productOrder = rule.apply(mugProducts)

        assertEquals(productOrder, mugProductOrder)
    }

    @Test
    fun `no promo for more than one mug`() {
        val rule = MugNoPromoRule()

        val productOrder = rule.apply(moreThanOneMug)

        assertEquals(productOrder, twoMugProductOrder)
    }


    companion object {
        private val mug = ProductBuilder().mug().build()
        private val tshirt = ProductBuilder().tshirt().build()
        private val voucher = ProductBuilder().voucher().build()

        private val noMugProducts = Products(listOf(tshirt, voucher))
        private val mugProducts = Products(listOf(mug, voucher, tshirt))
        private val moreThanOneMug = Products(listOf(mug, mug, voucher, tshirt))

        private val noPriceProduct =
            ProductOrderBuilder().mug().build().copy(price = Price.eurPrice(0.0))

        private val mugProductOrder = ProductOrderBuilder().mug()
            .build()
        private val twoMugProductOrder = ProductOrderBuilder().mug()
            .build().copy(price = Price.eurPrice(15.0))

    }
}