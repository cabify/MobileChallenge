package com.cabify.mobilechallenge.features.cart.domain.processor

import com.cabify.mobilechallenge.cart.domain.entity.CartEntity
import com.cabify.mobilechallenge.features.cart.domain.entity.Order
import com.cabify.shared.product.domain.entities.BulkyItemsPromotionEntity
import com.cabify.shared.product.domain.entities.ProductEntity
import com.cabify.shared.product.domain.entities.PromotionEntity

class BulkyItemsPromotionProcessor : PromotionProcessor {

    override fun process(
        cartItem: CartEntity.Item,
        product: ProductEntity,
        promotion: PromotionEntity
    ): Order.Item {
        promotion as BulkyItemsPromotionEntity

        val matchesPromotion = cartItem.quantity >= promotion.minimumQuantity
        val unitaryFinalPrice = if (matchesPromotion) {
            product.price - (product.price * promotion.discountPercentagePerItem / 100)
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