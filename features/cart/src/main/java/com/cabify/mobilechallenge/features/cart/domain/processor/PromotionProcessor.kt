package com.cabify.mobilechallenge.features.cart.domain.processor

import com.cabify.mobilechallenge.cart.domain.entity.CartEntity
import com.cabify.mobilechallenge.features.cart.domain.entity.Order
import com.cabify.shared.product.domain.entities.ProductEntity
import com.cabify.shared.product.domain.entities.PromotionEntity

interface PromotionProcessor {
    fun process(
        cartItem: CartEntity.Item,
        product: ProductEntity,
        promotion: PromotionEntity
    ): List<Order.Item>
}