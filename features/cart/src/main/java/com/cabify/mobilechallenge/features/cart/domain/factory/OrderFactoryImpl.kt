package com.cabify.mobilechallenge.features.cart.domain.factory

import com.cabify.mobilechallenge.cart.domain.entity.CartEntity
import com.cabify.mobilechallenge.features.cart.domain.entity.OrderEntity
import com.cabify.mobilechallenge.features.cart.domain.processor.PromotionProcessor
import com.cabify.shared.product.domain.entities.ProductEntity
import com.cabify.shared.product.domain.entities.PromotionEntity


class OrderFactoryImpl(private val promotionProcessors: Map<String, PromotionProcessor>) :
    OrderFactory {

    override fun create(
        cart: CartEntity, products: List<ProductEntity>, promotions: List<PromotionEntity>
    ): OrderEntity {
        val orderItems = createOrderItems(cart, products, promotions)
        val totalFinalPrice = orderItems.sumOf { it.unitFinalPrice * it.quantity }
        val totalBasePrice = orderItems.sumOf { it.unitBasePrice * it.quantity }
        return OrderEntity(
            orderId = DEFAULT_ORDER_ID,
            items = orderItems,
            totalBasePrice = totalBasePrice,
            totalFinalPrice = totalFinalPrice
        )
    }


    private fun createOrderItems(
        cart: CartEntity,
        products: List<ProductEntity>,
        promotions: List<PromotionEntity>
    ): List<OrderEntity.Item> {
        val productIdProductsMap = products.associateBy { it.id }
        val productIdPromotionMap = promotions.associateBy { it.productTargetId }

        return cart.items.fold(emptyList()) { acc, cartItem ->
            val product = productIdProductsMap[cartItem.productId] ?: return@fold acc
            val promotion = productIdPromotionMap[cartItem.productId]
            val promotionProcessor = promotionProcessors[promotion?.appInternalId]

            acc + if (promotionProcessor == null || promotion == null) {
                OrderEntity.Item(
                    productId = product.id,
                    productName = product.name,
                    unitBasePrice = product.price,
                    unitFinalPrice = product.price,
                    quantity = cartItem.quantity
                )
            } else {
                promotionProcessor.process(
                    cartItem,
                    product,
                    promotion
                )
            }
        }
    }

    companion object {
        //the orderID should be provided by the server but it is not the case and due to
        //we are handling one order maximum at the same time it will have the same id
        const val DEFAULT_ORDER_ID = "order_id"
    }
}