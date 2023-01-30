package com.cabify.challenge.core.domain.promos

import com.cabify.challenge.core.domain.cart.ProductOrder
import com.cabify.challenge.core.domain.products.Code.TSHIRT
import com.cabify.challenge.core.domain.products.Price
import com.cabify.challenge.core.domain.products.Product
import com.cabify.challenge.core.domain.products.Products

class TShirt3OrMoreDiscountRule : PromoRule {
    override fun apply(products: Products): ProductOrder {

        val vouchers = products.getProductsByCode(TSHIRT)
        val totalAmount = applyPromo(vouchers)

        return createProductOrder(totalAmount)
    }

    private fun applyPromo(vouchers: List<Product>): Double {
        val totalAmount = vouchers.map {
            if (vouchers.size >= 3) {
                PriceForBulkPurchases
            } else {
                it.price().amount()
            }
        }.reduceOrNull { a, b -> a + b } ?: 0.0
        return totalAmount
    }

    private fun createProductOrder(totalAmount: Double) = ProductOrder.new(
        Product(
            TSHIRT, TSHIRT.description, Price.eurPrice(totalAmount)
        )
    )

    companion object {
        private const val PriceForBulkPurchases = 19.0
    }


}