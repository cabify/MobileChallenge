package com.cabify.challenge.core.domain.promos

import com.cabify.challenge.builder.ProductBuilder
import com.cabify.challenge.builder.ProductOrderBuilder
import com.cabify.challenge.core.domain.products.Price
import com.cabify.challenge.core.domain.products.Products
import org.junit.Assert.assertEquals
import org.junit.Test

class TShirt3OrMoreDiscountRuleShould {
    @Test
    fun `not apply promo when there is no tshirt`() {
        val rule = TShirt3OrMoreDiscountRule()

        val productOrder = rule.apply(noTShirtsProducts)

        assertEquals(noPriceProduct, productOrder)
    }


    @Test
    fun `not apply promo`() {
        val rule = TShirt3OrMoreDiscountRule()

        val productOrder = rule.apply(noApplicableProducts)

        assertEquals(noAppliedPromo, productOrder)
    }

    @Test
    fun `apply promo for 2 vouchers`() {
        val rule = TShirt3OrMoreDiscountRule()

        val productOrder = rule.apply(applicable3orMoreProducts)

        assertEquals(productOrder, twoByOnePromo)
    }


    companion object {
        private val mug = ProductBuilder().mug().build()
        private val tshirt = ProductBuilder().tshirt().build()
        private val voucher = ProductBuilder().voucher().build()
        private val noApplicableProducts = Products(listOf(mug, tshirt, voucher))
        private val noTShirtsProducts = Products(listOf(mug, voucher))
        private val applicable3orMoreProducts = Products(listOf(mug, tshirt, tshirt, tshirt))

        private val noAppliedPromo = ProductOrderBuilder().tshirt().build()
        private val noPriceProduct =
            ProductOrderBuilder().tshirt().build().copy(price = Price.eurPrice(0.0))

        private val twoByOnePromo = ProductOrderBuilder().tshirt()
            .build().copy(price = Price(57.0))

    }
}