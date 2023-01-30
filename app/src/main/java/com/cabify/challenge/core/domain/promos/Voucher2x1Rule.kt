package com.cabify.challenge.core.domain.promos

import com.cabify.challenge.core.domain.cart.ProductOrder
import com.cabify.challenge.core.domain.products.Code.VOUCHER
import com.cabify.challenge.core.domain.products.Price
import com.cabify.challenge.core.domain.products.Product
import com.cabify.challenge.core.domain.products.Products

class Voucher2x1Rule : PromoRule {

    override fun apply(products: Products): ProductOrder {
        val vouchers = products.getProductsByCode(VOUCHER)

        val totalAmount = totalPriceOf(vouchers)

        return applyDiscount2x1Using(totalAmount, vouchers)
    }

    private fun applyDiscount2x1Using(addedAmount: Double, vouchers: List<Product>): ProductOrder {
        return if (addedAmount == 0.0) {
            createProductOrder(0.0)
        } else if (vouchers.size % 2 == 0) {
            createProductOrder(addedAmount.div(2))
        } else {
            val addedAmountPlusOne = addedAmount + vouchers[0].price().amount()
            createProductOrder(addedAmountPlusOne.div(2))
        }
    }

    private fun totalPriceOf(vouchers: List<Product>) =
        vouchers.map {
            it.price().amount()
        }.reduceOrNull { a, b ->
            a + b
        } ?: 0.0

    private fun createProductOrder(amount: Double): ProductOrder {
        return ProductOrder.new(
            Product(
                VOUCHER,
                VOUCHER.description,
                Price.eurPrice(amount)
            )
        )
    }


}