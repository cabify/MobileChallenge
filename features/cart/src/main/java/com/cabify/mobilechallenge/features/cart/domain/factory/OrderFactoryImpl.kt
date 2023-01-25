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
        val totalFinalPrice = orderItems.sumOf { it.unitaryFinalPrice * it.quantity }
        val totalBasePrice = orderItems.sumOf { it.unitaryBasePrice * it.quantity }
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
        val productIdProductsMap: Map<String, ProductEntity> = getProductMap(products)
        val productIdPromotionMap: Map<String, PromotionEntity> = getPromotionsMap(promotions)

        return cart.items.fold(emptyList()) { acc, cartItem ->

            val product = productIdProductsMap[cartItem.productId] ?: return@fold acc
            val promotion = productIdPromotionMap[cartItem.productId]
            val promotionProcessor = promotionProcessors[promotion?.appInternalId]

            if (promotionProcessor == null || promotion == null) {
                return@fold acc + Order.Item(
                    productId = product.id,
                    productName = product.name,
                    unitaryBasePrice = product.price,
                    unitaryFinalPrice = product.price,
                    quantity = cartItem.quantity
                )
            }

            acc + promotionProcessor.process(
                cartItem,
                product,
                promotion
            )
        }
    }

    private fun getProductMap(products: List<ProductEntity>): Map<String, ProductEntity> {
        return HashMap<String, ProductEntity>().apply {
            products.forEach {
                this[it.id] = it
            }
        }
    }

    private fun getPromotionsMap(promotions: List<PromotionEntity>): Map<String, PromotionEntity> {
        return HashMap<String, PromotionEntity>().apply {
            promotions.forEach {
                this[it.productTargetId] = it
            }
        }

    }
}