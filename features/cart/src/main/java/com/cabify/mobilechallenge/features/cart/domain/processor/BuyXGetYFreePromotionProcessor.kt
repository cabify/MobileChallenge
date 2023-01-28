package com.cabify.mobilechallenge.features.cart.domain.processor

import com.cabify.mobilechallenge.cart.domain.entity.CartEntity
import com.cabify.mobilechallenge.features.cart.domain.entity.OrderEntity
import com.cabify.shared.product.domain.entities.BuyXGetYFreePromotionEntity
import com.cabify.shared.product.domain.entities.ProductEntity
import com.cabify.shared.product.domain.entities.PromotionEntity

class BuyXGetYFreePromotionProcessor : PromotionProcessor {

    override fun process(
        cartItem: CartEntity.Item,
        product: ProductEntity,
        promotion: PromotionEntity
    ): OrderEntity.Item {
        if (promotion !is BuyXGetYFreePromotionEntity) throw java.lang.IllegalArgumentException("BuyXGetYFreePromotionProcessor only supports BuyXGetYFreePromotionEntity")

        val timesMatchingPromotion = cartItem.quantity / promotion.minimumQuantity
        val isMatchingPromotion = timesMatchingPromotion > 0

        val unitFinalPrice = if (isMatchingPromotion) {
            val freeItemsQuantity = timesMatchingPromotion * promotion.freeItemsQuantity
            val itemsToPay = cartItem.quantity - freeItemsQuantity
            (product.price * itemsToPay) / cartItem.quantity
        } else {
            product.price
        }

        return OrderEntity.Item(
            productId = product.id,
            productName = product.name,
            unitBasePrice = product.price,
            unitFinalPrice = unitFinalPrice,
            quantity = cartItem.quantity,
            promotion = if (isMatchingPromotion) promotion else null
        )
    }
}