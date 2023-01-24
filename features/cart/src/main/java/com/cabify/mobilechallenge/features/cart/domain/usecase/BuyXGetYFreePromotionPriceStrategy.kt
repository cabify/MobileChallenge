package com.cabify.mobilechallenge.features.cart.domain.usecase

import com.cabify.shared.product.domain.entities.BulkyItemsPromotionEntity
import com.cabify.shared.product.domain.entities.BuyXGetYFreePromotionEntity
import com.cabify.shared.product.domain.entities.PromotionEntity

class BuyXGetYFreePromotionPriceStrategy : PromotionPriceStrategy {
    override fun isMatching(promotion: PromotionEntity): Boolean =
        promotion is BuyXGetYFreePromotionEntity

    override fun apply(
        quantity: Int,
        productPrice: Double,
        promotion: PromotionEntity
    ): Double {
        promotion as BuyXGetYFreePromotionEntity
        val timesToApplyPromotion = quantity / promotion.minimumQuantity
        val freeItems = timesToApplyPromotion * promotion.freeItemsQuantity
        return productPrice * (quantity - freeItems)
    }
}