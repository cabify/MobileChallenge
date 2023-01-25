package com.cabify.mobilechallenge.features.cart.domain.processor

import com.cabify.mobilechallenge.cart.domain.entity.CartEntity
import com.cabify.mobilechallenge.features.cart.domain.entity.Order
import com.cabify.shared.product.domain.entities.BuyXGetYFreePromotionEntity
import com.cabify.shared.product.domain.entities.ProductEntity
import com.cabify.shared.product.domain.entities.PromotionEntity

class BuyXGetYFreePromotionProcessor : PromotionProcessor {

    override fun process(
        cartItem: CartEntity.Item,
        product: ProductEntity,
        promotion: PromotionEntity
    ): Order.Item {
        promotion as BuyXGetYFreePromotionEntity

        val timesToApplyPromotion = cartItem.quantity / promotion.minimumQuantity
        val matchesPromotion = timesToApplyPromotion > 0
        val freeItems = timesToApplyPromotion * promotion.freeItemsQuantity

        val unitaryFinalPrice = if (matchesPromotion) {
            (product.price * (cartItem.quantity - freeItems)) / cartItem.quantity
        } else {
            product.price
        }

        return Order.Item(
            productId = product.id,
            productName = product.name,
            quantity = cartItem.quantity,
            unitaryBasePrice = product.price,
            unitaryFinalPrice = unitaryFinalPrice,
            promotionName = if (matchesPromotion) promotion.name else null
        )
    }
}