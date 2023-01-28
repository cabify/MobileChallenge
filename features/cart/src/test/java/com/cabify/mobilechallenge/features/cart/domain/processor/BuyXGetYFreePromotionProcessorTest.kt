package com.cabify.mobilechallenge.features.cart.domain.processor

import com.cabify.mobilechallenge.cart.domain.entity.CartEntity
import com.cabify.mobilechallenge.features.cart.domain.entity.OrderEntity
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
    fun `GIVEN the cart item is matching the promotion 2x1 WHEN process THEN the order items are created with the BuyXGetYFree promotion applied`() {
        //GIVEN
        val buyXGetYFreePromotion = BuyXGetYFreePromotionEntity(
            id = PROMOTION_ID,
            name = PROMOTION_NAME,
            productTargetId = PRODUCT_ID_1,
            minimumQuantity = MINIMUM_QUANTITY_2,
            freeItemsQuantity = FREE_ITEMS_QUANTITY_1
        )

        //WHEN
        val cartItem = CartEntity.Item(
            productId = PRODUCT_ID_1,
            quantity = QUANTITY_2
        )
        val product = ProductEntity(
            id = PRODUCT_ID_1,
            name = PRODUCT_NAME_1,
            price = PRICE_1
        )
        val currentOrderItems = buyXGetYFreePromotionProcessor.process(
            cartItem = cartItem,
            product = product,
            promotion = buyXGetYFreePromotion
        )
        val expectedUnitFinalPrice = product.price / 2
        //THEN
        val expectedOrderItems = OrderEntity.Item(
            productId = product.id,
            productName = product.name,
            unitBasePrice = product.price,
            promotion = buyXGetYFreePromotion,
            unitFinalPrice = expectedUnitFinalPrice,
            quantity = cartItem.quantity
        )
        Assert.assertEquals(expectedOrderItems, currentOrderItems)
    }

    @Test
    fun `GIVEN the cart item is matching the promotion 3x2 WHEN process THEN the order items are created with the BuyXGetYFree promotion applied`() {
        //GIVEN
        val buyXGetYFreePromotion = BuyXGetYFreePromotionEntity(
            id = PROMOTION_ID,
            name = PROMOTION_NAME,
            productTargetId = PRODUCT_ID_1,
            minimumQuantity = MINIMUM_QUANTITY_3,
            freeItemsQuantity = FREE_ITEMS_QUANTITY_2
        )

        //WHEN
        val cartItem = CartEntity.Item(
            productId = PRODUCT_ID_1,
            quantity = QUANTITY_4
        )
        val product = ProductEntity(
            id = PRODUCT_ID_1,
            name = PRODUCT_NAME_1,
            price = PRICE_1
        )
        val currentOrderItems = buyXGetYFreePromotionProcessor.process(
            cartItem = cartItem,
            product = product,
            promotion = buyXGetYFreePromotion
        )

        //THEN
        val expectedUnitFinalPrice = product.price*2/ QUANTITY_4
        val expectedOrderItems = OrderEntity.Item(
            productId = product.id,
            productName = product.name,
            unitBasePrice = product.price,
            promotion = buyXGetYFreePromotion,
            unitFinalPrice = expectedUnitFinalPrice,
            quantity = cartItem.quantity
        )
        Assert.assertEquals(expectedOrderItems, currentOrderItems)
    }

    @Test
    fun `GIVEN the cart item is not matching the promotion WHEN process THEN order items are created without the BuyXGetYFree promotion applied`() {
        //GIVEN
        val cartItem = CartEntity.Item(
            productId = PRODUCT_ID_1,
            quantity = QUANTITY_1
        )
        val productEntity = ProductEntity(
            id = PRODUCT_ID_1,
            name = PRODUCT_NAME_1,
            price = PRICE_1
        )
        val promotion = BuyXGetYFreePromotionEntity(
            id = PROMOTION_ID,
            name = PROMOTION_NAME,
            productTargetId = PRODUCT_ID_1,
            minimumQuantity = MINIMUM_QUANTITY_2,
            freeItemsQuantity = FREE_ITEMS_QUANTITY_1
        )

        //WHEN
        val currentOrderItems = buyXGetYFreePromotionProcessor.process(
            cartItem = cartItem,
            product = productEntity,
            promotion = promotion
        )

        //THEN
        val expectedOrderItems = OrderEntity.Item(
            productId = productEntity.id,
            productName = productEntity.name,
            unitBasePrice = productEntity.price,
            unitFinalPrice = productEntity.price,
            promotion = null,
            quantity = cartItem.quantity
        )
        Assert.assertEquals(expectedOrderItems, currentOrderItems)
    }

    companion object {
        private const val PRODUCT_ID_1 = "1"
        private const val PRODUCT_NAME_1 = "product name 1"
        private const val PROMOTION_ID = "2x1"
        private const val PROMOTION_NAME = "2x1 promo"
        private const val PRICE_1 = 22.0
        private const val QUANTITY_2 = 2
        private const val QUANTITY_4 = 4
        private const val QUANTITY_1 = 1
        private const val MINIMUM_QUANTITY_2 = 2
        private const val MINIMUM_QUANTITY_3 = 3
        private const val FREE_ITEMS_QUANTITY_1 = 1
        private const val FREE_ITEMS_QUANTITY_2 = 2
    }
}