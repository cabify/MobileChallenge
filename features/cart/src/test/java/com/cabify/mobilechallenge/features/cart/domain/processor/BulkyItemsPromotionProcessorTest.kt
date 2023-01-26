package com.cabify.mobilechallenge.features.cart.domain.processor

import com.cabify.mobilechallenge.cart.domain.entity.CartEntity
import com.cabify.mobilechallenge.features.cart.domain.entity.OrderEntity
import com.cabify.shared.product.domain.entities.BulkyItemsPromotionEntity
import com.cabify.shared.product.domain.entities.ProductEntity
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BulkyItemsPromotionProcessorTest {
    private val bulkyItemsPromotionProcessor: BulkyItemsPromotionProcessor =
        BulkyItemsPromotionProcessor()

    @Test
    fun `GIVEN the cart item is matching the promotion WHEN process THEN the order items are created with the bulky promotion applied`() {
        val currentOrderItems = bulkyItemsPromotionProcessor.process(
            cartItem = CartEntity.Item(
                productId = PRODUCT_ID_1,
                quantity = QUANTITY_4
            ), product = ProductEntity(
                id = PRODUCT_ID_1,
                name = PRODUCT_NAME_1,
                price = PRICE_1
            ),
            promotion = BulkyItemsPromotionEntity(
                id = PROMOTION_ID,
                name = PROMOTION_NAME,
                productTargetId = PRODUCT_ID_1,
                minimumQuantity = MINIMUM_QUANTITY,
                discountPercentagePerItem = FIVE_PERCENTAGE
            )
        )
        val expectedFinalPrice = 20.9
        val expectedOrderItems = List(QUANTITY_4) {
            OrderEntity.Item(
                productId = PRODUCT_ID_1,
                productName = PRODUCT_NAME_1,
                basePrice = PRICE_1,
                finalPrice = expectedFinalPrice,
                promotionNameApplied = PROMOTION_NAME
            )
        }
        Assert.assertEquals(expectedOrderItems, currentOrderItems)
    }

    @Test
    fun `GIVEN the cart item is not matching the promotion WHEN process THEN order items are created without the promotion applied`() {
        val currentOrderItems = bulkyItemsPromotionProcessor.process(
            cartItem = CartEntity.Item(
                productId = PRODUCT_ID_1,
                quantity = QUANTITY_1
            ), product = ProductEntity(
                id = PRODUCT_ID_1,
                name = PRODUCT_NAME_1,
                price = PRICE_1
            ),
            promotion = BulkyItemsPromotionEntity(
                id = PROMOTION_ID,
                name = PROMOTION_NAME,
                productTargetId = PRODUCT_ID_1,
                minimumQuantity = MINIMUM_QUANTITY,
                discountPercentagePerItem = FIVE_PERCENTAGE
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
        private const val PROMOTION_ID = "Bulky"
        private const val PROMOTION_NAME = "Bulky"
        private const val PRICE_1 = 22.0
        private const val QUANTITY_4 = 4
        private const val QUANTITY_1 = 1
        private const val MINIMUM_QUANTITY = 4
        private const val FIVE_PERCENTAGE = 5
    }
}