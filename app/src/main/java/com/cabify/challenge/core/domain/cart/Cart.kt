package com.cabify.challenge.core.domain.cart

import com.cabify.challenge.core.domain.products.Code
import com.cabify.challenge.core.domain.products.Price
import com.cabify.challenge.core.domain.products.Products


data class Cart(
    private val products: Products,
    private val productsOrder: List<ProductOrder>
) {
    fun getProductsOrder(): List<ProductOrder> {
        return productsOrder
    }

    fun totalAmountNoPromo(): Price {
        return products.totalPrice()
    }

    fun totalAmountPromo(): Price {
        return if (productsOrder.isEmpty()) {
            totalIsZero()
        } else {
            addTheTotal()
        }
    }
    fun getItemsQuantityByCode(code: Code): Int {
        return products.getProductsByCode(code).size
    }

    private fun addTheTotal(): Price {
        val total = productsOrder.map {
            it.price().amount()
        }.reduce { a, b ->
            a + b
        }
        return Price.eurPrice(total)
    }

    private fun totalIsZero() = Price.eurPrice(0.0)


}