package com.cabify.challenge.builder

import com.cabify.challenge.core.domain.products.Code
import com.cabify.challenge.core.domain.products.Price
import com.cabify.challenge.core.domain.products.Product

class ProductBuilder {
    private var productCreated: Product? = null
    fun mug(): ProductBuilder {
        productCreated = mug
        return this
    }

    fun tshirt(): ProductBuilder {
        productCreated = tshirt
        return this
    }

    fun voucher(): ProductBuilder {
        productCreated = voucher
        return this
    }

    fun build(): Product {
        if (productCreated == null) {
            throw BuilderException()
        }
        return productCreated!!
    }


    companion object {
        val mug = Product(
            code = Code.MUG,
            name = "Cabify Coffee Mug",
            price = Price.eurPrice(amount = 7.5)
        )

        val tshirt = Product(
            code = Code.TSHIRT,
            name = "Cabify Coffee Mug",
            price = Price.eurPrice(amount = 20.0)
        )

        val voucher = Product(
            code = Code.VOUCHER,
            name = "Cabify Voucher",
            price = Price.eurPrice(amount = 5.0)
        )
    }
}

class BuilderException : Throwable(message = "You have to build some product")