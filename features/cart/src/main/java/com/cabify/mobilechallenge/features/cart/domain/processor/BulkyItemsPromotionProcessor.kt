package com.cabify.mobilechallenge.features.cart.domain.processor

import com.cabify.mobilechallenge.cart.domain.entity.CartEntity
import com.cabify.mobilechallenge.features.cart.domain.entity.OrderEntity
import com.cabify.shared.product.domain.entities.BulkyItemsPromotionEntity
import com.cabify.shared.product.domain.entities.ProductEntity
import com.cabify.shared.product.domain.entities.PromotionEntity

class BulkyItemsPromotionProcessor : PromotionProcessor {
    override fun process(
        cartItem: CartEntity.Item,
        product: ProductEntity,
        promotion: PromotionEntity
    ): OrderEntity.Item {
        if (promotion !is BulkyItemsPromotionEntity) throw java.lang.IllegalArgumentException("BulkyItemsPromotionProcessor only supports BulkyItemsPromotionEntity")

        val isMatchingPromotion = cartItem.quantity >= promotion.minimumQuantity

        val finalPrice = if (isMatchingPromotion) {
            product.price - (product.price * promotion.discountPercentagePerItem / 100)
        } else {
            product.price
        }

        return OrderEntity.Item(
            productId = product.id,
            productName = product.name,
            unitBasePrice = product.price,
            unitFinalPrice = finalPrice,
            quantity = cartItem.quantity,
            promotion = if (isMatchingPromotion) promotion else null,
            productImageUrl = product.productImageUrl
        )
    }
}