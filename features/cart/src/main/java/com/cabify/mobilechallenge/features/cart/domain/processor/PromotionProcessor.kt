package com.cabify.mobilechallenge.features.cart.domain.processor

import com.cabify.mobilechallenge.shared.cart.domain.entity.CartEntity
import com.cabify.mobilechallenge.features.cart.domain.entity.OrderEntity
import com.cabify.shared.product.domain.entities.ProductEntity
import com.cabify.shared.product.domain.entities.PromotionEntity

interface PromotionProcessor {
    fun process(
        cartItem: CartEntity.Item,
        product: ProductEntity,
        promotion: PromotionEntity
    ): OrderEntity.Item
}