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
    ): List<Order.Item> {
        promotion as BulkyItemsPromotionEntity

        val isMatchingPromotion = cartItem.quantity >= promotion.minimumQuantity

        val finalPrice = if (isMatchingPromotion) {
            product.price - (product.price * promotion.discountPercentagePerItem / 100)
        } else {
            product.price
        }

        return List(cartItem.quantity) {
            Order.Item(
                productId = product.id,
                productName = product.name,
                basePrice = product.price,
                finalPrice = finalPrice,
                promotionNameApplied = if (isMatchingPromotion) promotion.name else null
            )
        }
    }
}