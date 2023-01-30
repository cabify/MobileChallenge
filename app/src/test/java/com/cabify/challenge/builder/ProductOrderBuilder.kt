package com.cabify.challenge.builder

import com.cabify.challenge.core.domain.cart.ProductOrder
import com.cabify.challenge.core.domain.products.Code
import com.cabify.challenge.core.domain.products.Price

class ProductOrderBuilder {
    private var productCreated: ProductOrder? = null
    fun mug(): ProductOrderBuilder {
        productCreated = mug
        return this
    }

    fun tshirt(): ProductOrderBuilder {
        productCreated = tshirt
        return this
    }

    fun voucher(): ProductOrderBuilder {
        productCreated = voucher
        return this
    }

    fun build(): ProductOrder {
        if (productCreated == null) {
            throw BuilderException()
        }
        return productCreated!!
    }


    companion object {
        val mug = ProductOrder(
            code = Code.MUG,
            name = "Cabify Coffee Mug",
            price = Price.eurPrice(amount = 7.5)
        )

        val tshirt = ProductOrder(
            code = Code.TSHIRT,
            name = "Cabify T-Shirt",
            price = Price.eurPrice(amount = 20.0)
        )

        val voucher = ProductOrder(
            code = Code.VOUCHER,
            name = "Cabify Voucher",
            price = Price.eurPrice(amount = 5.0)
        )
    }
}