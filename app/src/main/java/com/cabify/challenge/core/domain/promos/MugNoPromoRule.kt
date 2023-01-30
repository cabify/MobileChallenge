package com.cabify.challenge.core.domain.promos

import com.cabify.challenge.core.domain.cart.ProductOrder
import com.cabify.challenge.core.domain.products.Code.MUG
import com.cabify.challenge.core.domain.products.Price
import com.cabify.challenge.core.domain.products.Product
import com.cabify.challenge.core.domain.products.Products

class MugNoPromoRule : PromoRule {
    override fun apply(products: Products): ProductOrder {
        val vouchers = products.getProductsByCode(MUG)
        val totalAmount = vouchers.map {
            it.price().amount()
        }.reduceOrNull { a, b ->
            a + b
        } ?: 0.0
        return createProductOrder(totalAmount)
    }

    private fun createProductOrder(totalAmount: Double) = ProductOrder.new(
        Product(
            MUG, MUG.description, Price.eurPrice(totalAmount)
        )
    )
}