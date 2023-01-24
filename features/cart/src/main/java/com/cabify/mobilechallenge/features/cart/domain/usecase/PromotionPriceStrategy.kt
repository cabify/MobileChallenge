package com.cabify.mobilechallenge.features.cart.domain.usecase

import com.cabify.shared.product.domain.entities.PromotionEntity

interface PromotionPriceStrategy {
    fun isMatching(promotion: PromotionEntity): Boolean
    fun apply(quantity: Int, productPrice: Double,promotion: PromotionEntity): Double
}