package com.cabify.challenge.core.domain.services

import com.cabify.challenge.core.domain.cart.Cart
import com.cabify.challenge.core.domain.cart.ProductOrder
import com.cabify.challenge.core.domain.products.Products
import com.cabify.challenge.core.domain.promos.PromoRule

class RulePromosService(private val rules: List<PromoRule>) : PromosService {
    override fun apply(products: Products): Cart {
        val productsOrder = mutableListOf<ProductOrder>()
        rules.forEach {
            val order = it.apply(products)
            productsOrder.add(order)
        }

        return Cart(products, productsOrder)
    }
}