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
    fun `GIVEN the cart item is matching the promotion WHEN process THEN the order item is created with the bulky promotion applied`() {
        //GIVEN
        val bulkyPromotion = BulkyItemsPromotionEntity(
            id = PROMOTION_ID,
            name = PROMOTION_NAME,
            productTargetId = PRODUCT_ID_1,
            minimumQuantity = MINIMUM_QUANTITY_4,
            discountPercentagePerItem = FIVE_PERCENTAGE
        )
        val cartItem = CartEntity.Item(
            productId = PRODUCT_ID_1,
            quantity = QUANTITY_4
        )
        val product = ProductEntity(
            id = PRODUCT_ID_1,
            name = PRODUCT_NAME_1,
            price = PRICE_1,
            productImageUrl = ANY_PRODUCT_IMAGE_URL
        )

        //WHEN
        val currentOrderItem = bulkyItemsPromotionProcessor.process(
            cartItem = cartItem,
            product = product,
            promotion = bulkyPromotion
        )

        //THEN
        val expectedFinalPrice = 20.9

        val expectedOrderItems = OrderEntity.Item(
            productId = product.id,
            productName = product.name,
            unitBasePrice = product.price,
            unitFinalPrice = expectedFinalPrice,
            promotion = bulkyPromotion,
            quantity = cartItem.quantity,
            productImageUrl = ANY_PRODUCT_IMAGE_URL
        )
        Assert.assertEquals(expectedOrderItems, currentOrderItem)
    }

    @Test
    fun `GIVEN the cart item is not matching the promotion WHEN process THEN order items are created without the bulky promotion applied`() {
        //GIVEN
        val cartItem = CartEntity.Item(
            productId = PRODUCT_ID_1,
            quantity = QUANTITY_1
        )
        val product = ProductEntity(
            id = PRODUCT_ID_1,
            name = PRODUCT_NAME_1,
            price = PRICE_1,
            productImageUrl = ANY_PRODUCT_IMAGE_URL
        )
        val promotion = BulkyItemsPromotionEntity(
            id = PROMOTION_ID,
            name = PROMOTION_NAME,
            productTargetId = PRODUCT_ID_1,
            minimumQuantity = MINIMUM_QUANTITY_4,
            discountPercentagePerItem = FIVE_PERCENTAGE
        )

        //WHEN
        val currentOrderItems = bulkyItemsPromotionProcessor.process(
            cartItem = cartItem,
            product = product,
            promotion = promotion
        )

        //THEN
        val expectedOrderItem = OrderEntity.Item(
            productId = product.id,
            productName = product.name,
            unitBasePrice = product.price,
            unitFinalPrice = product.price,
            promotion = null,
            quantity = cartItem.quantity,
            productImageUrl = ANY_PRODUCT_IMAGE_URL
        )

        Assert.assertEquals(expectedOrderItem, currentOrderItems)
    }

    companion object {
        private const val PRODUCT_ID_1 = "1"
        private const val PRODUCT_NAME_1 = "product name 1"
        private const val PROMOTION_ID = "Bulky"
        private const val PROMOTION_NAME = "Bulky"
        private const val ANY_PRODUCT_IMAGE_URL ="any_pic"
        private const val PRICE_1 = 22.0
        private const val QUANTITY_4 = 4
        private const val QUANTITY_1 = 1
        private const val MINIMUM_QUANTITY_4 = 4
        private const val FIVE_PERCENTAGE = 5
    }
}