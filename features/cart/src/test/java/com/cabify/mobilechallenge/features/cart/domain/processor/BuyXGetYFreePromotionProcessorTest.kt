package com.cabify.mobilechallenge.features.cart.domain.processor

import com.cabify.mobilechallenge.cart.domain.entity.CartEntity
import com.cabify.mobilechallenge.features.cart.domain.entity.OrderEntity
import com.cabify.shared.product.domain.entities.BulkyItemsPromotionEntity
import com.cabify.shared.product.domain.entities.BuyXGetYFreePromotionEntity
import com.cabify.shared.product.domain.entities.ProductEntity
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BuyXGetYFreePromotionProcessorTest {
    private val buyXGetYFreePromotionProcessor: BuyXGetYFreePromotionProcessor =
        BuyXGetYFreePromotionProcessor()

    @Test
    fun `GIVEN the cart item is matching the promotion WHEN process THEN the order items are created with the BuyXGetYFree promotion applied`() {
        val currentOrderItems = buyXGetYFreePromotionProcessor.process(
            cartItem = CartEntity.Item(
                productId = PRODUCT_ID_1,
                quantity = QUANTITY_2
            ), product = ProductEntity(
                id = PRODUCT_ID_1,
                name = PRODUCT_NAME_1,
                price = PRICE_1
            ),
            promotion = BuyXGetYFreePromotionEntity(
                id = PROMOTION_ID,
                name = PROMOTION_NAME,
                productTargetId = PRODUCT_ID_1,
                minimumQuantity = MINIMUM_QUANTITY,
                freeItemsQuantity = FREE_ITEMS_QUANTITY
            )
        )
        val expectedOrderItems = listOf(
            OrderEntity.Item(
                productId = PRODUCT_ID_1,
                productName = PRODUCT_NAME_1,
                basePrice = PRICE_1,
                finalPrice = 0.0,
                promotionNameApplied = PROMOTION_NAME
            ),
            OrderEntity.Item(
                productId = PRODUCT_ID_1,
                productName = PRODUCT_NAME_1,
                basePrice = PRICE_1,
                finalPrice = PRICE_1,
                promotionNameApplied = PROMOTION_NAME
            )
        )
        Assert.assertEquals(expectedOrderItems, currentOrderItems)
    }

    @Test
    fun `GIVEN the cart item is not matching the promotion WHEN process THEN order items are created without the BuyXGetYFree promotion applied`() {
        val currentOrderItems = buyXGetYFreePromotionProcessor.process(
            cartItem = CartEntity.Item(
                productId = PRODUCT_ID_1,
                quantity = QUANTITY_1
            ), product = ProductEntity(
                id = PRODUCT_ID_1,
                name = PRODUCT_NAME_1,
                price = PRICE_1
            ),
            promotion = BuyXGetYFreePromotionEntity(
                id = PROMOTION_ID,
                name = PROMOTION_NAME,
                productTargetId = PRODUCT_ID_1,
                minimumQuantity = MINIMUM_QUANTITY,
                freeItemsQuantity = FREE_ITEMS_QUANTITY
            )
        )
        val expectedOrderItems = List(QUANTITY_1) {
            OrderEntity.Item(
                productId = PRODUCT_ID_1,
                productName = PRODUCT_NAME_1,
                basePrice = PRICE_1,
                finalPrice = PRICE_1,
                promotionNameApplied = null
            )
        }
        Assert.assertEquals(expectedOrderItems, currentOrderItems)
    }

    companion object {
        private const val PRODUCT_ID_1 = "1"
        private const val PRODUCT_NAME_1 = "product name 1"
        private const val PROMOTION_ID = "2x1"
        private const val PROMOTION_NAME = "2x1 promo"
        private const val PRICE_1 = 22.0
        private const val QUANTITY_2 = 2
        private const val QUANTITY_1 = 1
        private const val MINIMUM_QUANTITY = 2
        private const val FREE_ITEMS_QUANTITY = 1
    }
}