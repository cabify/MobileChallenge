package com.cabify.challenge.core.domain.cart

import com.cabify.challenge.core.domain.products.Code
import com.cabify.challenge.core.domain.products.Price
import com.cabify.challenge.core.domain.products.Product

data class ProductOrder(
    private val code: Code,
    private val name: String,
    private val price: Price
) {
    fun price(): Price {
        return price
    }

    fun presentPrice(): String {
        return price.presentPrice()
    }

    fun name(): String {
        return name
    }

    fun code(): Code {
        return code
    }

    companion object {
        fun new(product: Product): ProductOrder {
            return ProductOrder(
                code = product.code(),
                name = product.name(),
                price = product.price()
            )
        }
    }

}