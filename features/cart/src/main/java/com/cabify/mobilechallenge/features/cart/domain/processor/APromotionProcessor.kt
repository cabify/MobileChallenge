package com.cabify.mobilechallenge.features.cart.domain.processor

import com.cabify.mobilechallenge.features.cart.domain.entity.Order
import com.cabify.shared.product.domain.entities.PromotionEntity

interface APromotionProcessor {
    fun process(order: Order, promotions: List<PromotionEntity>): Order
}