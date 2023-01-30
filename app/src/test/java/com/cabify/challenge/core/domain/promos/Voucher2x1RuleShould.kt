package com.cabify.challenge.core.domain.promos

import com.cabify.challenge.builder.ProductBuilder
import com.cabify.challenge.builder.ProductOrderBuilder
import com.cabify.challenge.core.domain.products.Price
import com.cabify.challenge.core.domain.products.Products
import org.junit.Assert.assertEquals
import org.junit.Test

class Voucher2x1RuleShould {

    @Test
    fun `not apply promo when there is no vouchers`() {
        val rule = Voucher2x1Rule()

        val productOrder = rule.apply(noVouchersProducts)

        assertEquals(productOrder, noPriceProduct)
    }


    @Test
    fun `not apply promo`() {
        val rule = Voucher2x1Rule()

        val productOrder = rule.apply(noApplicableProducts)

        assertEquals(productOrder, noAppliedPromo)
    }

    @Test
    fun `apply promo for 2 vouchers`() {
        val rule = Voucher2x1Rule()

        val productOrder = rule.apply(applicable2x1Products)

        assertEquals(productOrder, twoByOnePromo)
    }

    @Test
    fun `apply promo for 3 vouchers`() {
        val rule = Voucher2x1Rule()

        val productOrder = rule.apply(applicable2x1With3VouchersProducts)

        assertEquals(productOrder, twoByOneWith3VouchersPromo)
    }

    companion object {
        private val mug = ProductBuilder().mug().build()
        private val tshirt = ProductBuilder().tshirt().build()
        private val voucher = ProductBuilder().voucher().build()
        private val noApplicableProducts = Products(listOf(mug, tshirt, voucher))
        private val noVouchersProducts = Products(listOf(mug, tshirt))
        private val applicable2x1Products = Products(listOf(mug, tshirt, voucher, voucher))
        private val applicable2x1With3VouchersProducts =
            Products(listOf(mug, tshirt, voucher, voucher, voucher))

        private val noAppliedPromo = ProductOrderBuilder().voucher().build()
        private val noPriceProduct =
            ProductOrderBuilder().voucher().build().copy(price = Price.eurPrice(0.0))

        private val twoByOnePromo = ProductOrderBuilder().voucher()
            .build().copy(price = Price(5.0))
        private val twoByOneWith3VouchersPromo = ProductOrderBuilder().voucher()
            .build().copy(price = Price(10.0))
    }
}