package com.cabify.mobilechallenge.features.cart.domain.factory

import com.cabify.mobilechallenge.cart.domain.entity.CartEntity
import com.cabify.mobilechallenge.features.cart.domain.entity.OrderEntity
import com.cabify.shared.product.domain.entities.ProductEntity
import com.cabify.shared.product.domain.entities.PromotionEntity

interface OrderFactory {
    fun create(
        cart: CartEntity,
        products: List<ProductEntity>,
        promotions: List<PromotionEntity>
    ): OrderEntity
}