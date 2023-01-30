package com.cabify.challenge.core.domain.promos

import com.cabify.challenge.core.domain.cart.ProductOrder
import com.cabify.challenge.core.domain.products.Products

interface PromoRule {
    fun apply(products: Products): ProductOrder
}