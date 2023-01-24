package com.cabify.mobilechallenge.features.cart.domain.usecase

import com.cabify.shared.product.domain.entities.BulkyItemsPromotionEntity
import com.cabify.shared.product.domain.entities.PromotionEntity

class BulkyItemsPromotionPriceStrategy : PromotionPriceStrategy {

    override fun isMatching(promotion: PromotionEntity): Boolean =
        promotion is BulkyItemsPromotionEntity

    override fun apply(quantity: Int, productPrice: Double, promotion: PromotionEntity): Double {
        promotion as BulkyItemsPromotionEntity
        return if (quantity >= promotion.minimumQuantity) {
            (productPrice - (productPrice * promotion.discountPercentagePerItem / 100)) * quantity
        } else {
            productPrice * quantity
        }
    }
}