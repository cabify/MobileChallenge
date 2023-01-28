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
        promotion as BuyXGetYFreePromotionEntity

        val timesMatchingPromotion = cartItem.quantity / promotion.minimumQuantity
        val isMatchingPromotion = timesMatchingPromotion > 0

        val unitFinalPrice = if (isMatchingPromotion) {
            val freeItemsQuantity = timesMatchingPromotion * promotion.freeItemsQuantity
            val itemsInsidePromotionQuantity = timesMatchingPromotion * promotion.minimumQuantity
            val itemsOutsidePromotion = cartItem.quantity - itemsInsidePromotionQuantity

            val priceUnitPerPromotionItems =
                (product.price * (itemsInsidePromotionQuantity - freeItemsQuantity)) / (itemsInsidePromotionQuantity)

            val priceUnitPerOutsidePromotionItems =
                if (itemsOutsidePromotion > 0) product.price * itemsOutsidePromotion / itemsOutsidePromotion else 0.0

            priceUnitPerOutsidePromotionItems + priceUnitPerPromotionItems

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