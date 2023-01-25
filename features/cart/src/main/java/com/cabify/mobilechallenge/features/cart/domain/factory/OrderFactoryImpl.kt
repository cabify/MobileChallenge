package com.cabify.mobilechallenge.features.cart.domain.factory

import com.cabify.mobilechallenge.cart.domain.entity.CartEntity
import com.cabify.mobilechallenge.features.cart.domain.entity.Order
import com.cabify.mobilechallenge.features.cart.domain.processor.PromotionProcessor
import com.cabify.shared.product.domain.entities.ProductEntity
import com.cabify.shared.product.domain.entities.PromotionEntity


class OrderFactoryImpl(private val promotionProcessors: Map<String, PromotionProcessor>) :
    OrderFactory {

    override fun create(
        cart: CartEntity, products: List<ProductEntity>, promotions: List<PromotionEntity>
    ): Order {
        val orderItems = createOrderItems(cart, products, promotions)
        val totalFinalPrice = orderItems.sumOf { it.finalPrice }
        val totalBasePrice = orderItems.sumOf { it.basePrice }
        return Order(
            items = orderItems,
            totalBasePrice = totalBasePrice,
            totalFinalPrice = totalFinalPrice
        )
    }


    private fun createOrderItems(
        cart: CartEntity,
        products: List<ProductEntity>,
        promotions: List<PromotionEntity>
    ): List<Order.Item> {
        val productIdProductsMap = products.associateBy { it.id }
        val productIdPromotionMap = promotions.associateBy { it.productTargetId }

        return cart.items.fold(emptyList()) { acc, cartItem ->
            val product = productIdProductsMap[cartItem.productId] ?: return@fold acc
            val promotion = productIdPromotionMap[cartItem.productId]
            val promotionProcessor = promotionProcessors[promotion?.appInternalId]

            acc + if (promotionProcessor == null || promotion == null) {
                List(cartItem.quantity) {
                    Order.Item(
                        productId = product.id,
                        productName = product.name,
                        basePrice = product.price,
                        finalPrice = product.price
                    )
                }
            } else {
                promotionProcessor.process(
                    cartItem,
                    product,
                    promotion
                )
            }
        }
    }
}