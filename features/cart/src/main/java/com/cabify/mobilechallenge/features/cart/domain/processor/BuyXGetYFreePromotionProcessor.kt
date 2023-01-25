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
    ): List<Order.Item> {
        promotion as BuyXGetYFreePromotionEntity

        val promotionsAppliedQuantity =
            cartItem.quantity / promotion.minimumQuantity

        val freeItemsQuantity =
            promotionsAppliedQuantity * promotion.freeItemsQuantity

        val paidItemsOutsidePromotionQuantity =
            cartItem.quantity % promotion.minimumQuantity

        val paidItemsInsidePromotionQuantity =
            (promotion.minimumQuantity - promotion.freeItemsQuantity) * promotionsAppliedQuantity

        return createFreeItems(freeItemsQuantity, product, promotion) +
            createPaidItemsOutsidePromotion(paidItemsOutsidePromotionQuantity, product) +
            createPaidItemsInsidePromotion(paidItemsInsidePromotionQuantity, product, promotion)
    }

    private fun createPaidItemsInsidePromotion(
        paidItemsInsidePromotionQuantity: Int,
        product: ProductEntity,
        promotion: PromotionEntity
    ) = List(paidItemsInsidePromotionQuantity) {
        Order.Item(
            productId = product.id,
            productName = product.name,
            basePrice = product.price,
            finalPrice = product.price,
            promotionNameApplied = promotion.name
        )
    }

    private fun createFreeItems(
        freeItemsQuantity: Int,
        product: ProductEntity,
        promotion: PromotionEntity
    ) = List(freeItemsQuantity) {
        Order.Item(
            productId = product.id,
            productName = product.name,
            basePrice = product.price,
            finalPrice = FREE_ITEM_PRICE,
            promotionNameApplied = promotion.name
        )
    }

    private fun createPaidItemsOutsidePromotion(
        paidItemsOutsidePromotionQuantity: Int,
        product: ProductEntity
    ) = List(paidItemsOutsidePromotionQuantity) {
        Order.Item(
            productId = product.id,
            productName = product.name,
            basePrice = product.price,
            finalPrice = product.price
        )
    }

    companion object {
        private const val FREE_ITEM_PRICE = 0.0
    }
}