package com.cabify.mobilechallenge.features.cart.domain.factory

import com.cabify.mobilechallenge.cart.domain.entity.CartEntity
import com.cabify.mobilechallenge.features.cart.domain.entity.Order
import com.cabify.mobilechallenge.features.cart.domain.processor.PromotionProcessor
import com.cabify.shared.product.domain.entities.BulkyItemsPromotionEntity
import com.cabify.shared.product.domain.entities.BuyXGetYFreePromotionEntity
import com.cabify.shared.product.domain.entities.ProductEntity
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class OrderFactoryImplTest {

    companion object {
        private const val PRODUCT_ID_1 = "1"
        private const val PRODUCT_ID_2 = "2"
        private const val PRODUCT_ID_3 = "3"

        private const val PRODUCT_NAME_1 = "1"
        private const val PRODUCT_NAME_2 = "2"
        private const val PRODUCT_NAME_3 = "3"

        private const val PRODUCT_PRICE_1 = 30.0
        private const val PRODUCT_PRICE_1_WITH_DISCOUNT = 28.0
        private const val PRODUCT_PRICE_2 = 23.0
        private const val PRODUCT_PRICE_2_WITH_DISCOUNT = 22.0
        private const val PRODUCT_PRICE_3 = 10.0

        private const val PROMOTION_NAME_1 = "PROMOTION_NAME_1"
        private const val PROMOTION_NAME_2 = "PROMOTION_NAME_2"
    }

    private val bulkyPromotionProcessor: PromotionProcessor = mock()
    private val buyXGetYFreePromotionProcessor: PromotionProcessor = mock()

    private val orderFactoryImpl: OrderFactoryImpl = OrderFactoryImpl(
        promotionProcessors = mapOf(
            BulkyItemsPromotionEntity.APP_INTERNAL_ID to bulkyPromotionProcessor,
            BuyXGetYFreePromotionEntity.APP_INTERNAL_ID to buyXGetYFreePromotionProcessor
        )
    )

    private val cartItem = CartEntity.Item(productId = PRODUCT_ID_1, quantity = 1)
    private val cartItems = listOf(cartItem)
    private val cartEntity = CartEntity(items = cartItems)


    @Test
    fun `GIVEN the cart contains orders WHEN create order THEN the order contains these items AND final prices`() {
        whenever(bulkyPromotionProcessor.process(any(), any(), any())) doReturn listOf(
            Order.Item(
                productId = PRODUCT_ID_1,
                productName = PRODUCT_NAME_1,
                basePrice = PRODUCT_PRICE_1,
                finalPrice = PRODUCT_PRICE_1_WITH_DISCOUNT,
                promotionNameApplied = PROMOTION_NAME_1
            )
        )
        whenever(buyXGetYFreePromotionProcessor.process(any(), any(), any())) doReturn listOf(
            Order.Item(
                productId = PRODUCT_ID_2,
                productName = PRODUCT_NAME_2,
                basePrice = PRODUCT_PRICE_2,
                finalPrice = PRODUCT_PRICE_2_WITH_DISCOUNT,
                promotionNameApplied = PROMOTION_NAME_2
            )
        )

        val currentOrder = orderFactoryImpl.create(
            cart = cartEntity.copy(
                items = listOf(
                    cartItem.copy(productId = PRODUCT_ID_1),
                    cartItem.copy(productId = PRODUCT_ID_2),
                    cartItem.copy(productId = PRODUCT_ID_3)
                )
            ),
            products = listOf(
                ProductEntity(
                    id = PRODUCT_ID_1,
                    name = PRODUCT_NAME_1,
                    price = PRODUCT_PRICE_1
                ),
                ProductEntity(
                    id = PRODUCT_ID_2,
                    name = PRODUCT_NAME_2,
                    price = PRODUCT_PRICE_2
                ),
                ProductEntity(
                    id = PRODUCT_ID_3,
                    name = PRODUCT_NAME_3,
                    price = PRODUCT_PRICE_3
                )
            ),
            promotions = listOf(
                BulkyItemsPromotionEntity(
                    id = "",
                    name = "",
                    productTargetId = PRODUCT_ID_1,
                    minimumQuantity = 0,
                    discountPercentagePerItem = 0
                ),
                BuyXGetYFreePromotionEntity(
                    id = "",
                    name = "",
                    productTargetId = PRODUCT_ID_2,
                    minimumQuantity = 0,
                    freeItemsQuantity = 0
                )
            )
        )

        val expectedOrder = Order(
            items = listOf(
                Order.Item(
                    productId = PRODUCT_ID_1,
                    productName = PRODUCT_NAME_1,
                    basePrice = PRODUCT_PRICE_1,
                    finalPrice = PRODUCT_PRICE_1_WITH_DISCOUNT,
                    promotionNameApplied = PROMOTION_NAME_1
                ),
                Order.Item(
                    productId = PRODUCT_ID_2,
                    productName = PRODUCT_NAME_2,
                    basePrice = PRODUCT_PRICE_2,
                    finalPrice = PRODUCT_PRICE_2_WITH_DISCOUNT,
                    promotionNameApplied = PROMOTION_NAME_2
                ),
                Order.Item(
                    productId = PRODUCT_ID_3,
                    productName = PRODUCT_NAME_3,
                    basePrice = PRODUCT_PRICE_3,
                    finalPrice = PRODUCT_PRICE_3,
                    promotionNameApplied = null
                )
            ),
            totalBasePrice = 63.0,
            totalFinalPrice = 60.0
        )

        Assert.assertEquals(expectedOrder, currentOrder)
    }

    @Test
    fun `GIVEN the cart is empty WHEN create order THEN the order is created without items AND with zero prices`() {
        val currentOrder = orderFactoryImpl.create(
            cart = cartEntity.copy(
                items = listOf()
            ),
            products = listOf(
                ProductEntity(
                    id = PRODUCT_ID_1,
                    name = PRODUCT_NAME_1,
                    price = PRODUCT_PRICE_1
                ),
                ProductEntity(
                    id = PRODUCT_ID_2,
                    name = PRODUCT_NAME_2,
                    price = PRODUCT_PRICE_2
                ),
                ProductEntity(
                    id = PRODUCT_ID_3,
                    name = PRODUCT_NAME_3,
                    price = PRODUCT_PRICE_3
                )
            ),
            promotions = listOf(
                BulkyItemsPromotionEntity(
                    id = "",
                    name = "",
                    productTargetId = PRODUCT_ID_1,
                    minimumQuantity = 0,
                    discountPercentagePerItem = 0
                ),
                BuyXGetYFreePromotionEntity(
                    id = "",
                    name = "",
                    productTargetId = PRODUCT_ID_2,
                    minimumQuantity = 0,
                    freeItemsQuantity = 0
                )
            )
        )

        val expectedOrder = Order(
            items = emptyList(),
            totalBasePrice = 0.0,
            totalFinalPrice = 0.0
        )

        Assert.assertEquals(expectedOrder, currentOrder)
    }

    @Test
    fun `GIVEN the cart item does not correspond to any current available product WHEN create order THEN this item is not added in the order`() {
        whenever(buyXGetYFreePromotionProcessor.process(any(), any(), any())) doReturn listOf(
            Order.Item(
                productId = PRODUCT_ID_2,
                productName = PRODUCT_NAME_2,
                basePrice = PRODUCT_PRICE_2,
                finalPrice = PRODUCT_PRICE_2_WITH_DISCOUNT,
                promotionNameApplied = PROMOTION_NAME_2
            )
        )

        val currentOrder = orderFactoryImpl.create(
            cart = cartEntity.copy(
                items = listOf(
                    cartItem.copy(productId = PRODUCT_ID_1),
                    cartItem.copy(productId = PRODUCT_ID_2)
                )
            ),
            products = listOf(
                ProductEntity(
                    id = PRODUCT_ID_2,
                    name = PRODUCT_NAME_2,
                    price = PRODUCT_PRICE_2
                )
            ),
            promotions = listOf(
                BulkyItemsPromotionEntity(
                    id = "",
                    name = "",
                    productTargetId = PRODUCT_ID_1,
                    minimumQuantity = 0,
                    discountPercentagePerItem = 0
                ),
                BuyXGetYFreePromotionEntity(
                    id = "",
                    name = "",
                    productTargetId = PRODUCT_ID_2,
                    minimumQuantity = 0,
                    freeItemsQuantity = 0
                )
            )
        )

        val expectedOrder = Order(
            items = listOf(
                Order.Item(
                    productId = PRODUCT_ID_2,
                    productName = PRODUCT_NAME_2,
                    basePrice = PRODUCT_PRICE_2,
                    finalPrice = PRODUCT_PRICE_2_WITH_DISCOUNT,
                    promotionNameApplied = PROMOTION_NAME_2
                )
            ),
            totalBasePrice = 23.0,
            totalFinalPrice = 22.0
        )

        Assert.assertEquals(expectedOrder, currentOrder)
    }

    @Test
    fun `GIVEN a cart item with a promotion associated BUT without promotion processor defined WHEN create order THEN the default prices are applied`() {

        val currentOrder = OrderFactoryImpl(
            promotionProcessors = mapOf()
        ).create(
            cart = cartEntity.copy(
                items = listOf(
                    cartItem.copy(productId = PRODUCT_ID_1),
                    cartItem.copy(productId = PRODUCT_ID_2)
                )
            ),
            products = listOf(
                ProductEntity(
                    id = PRODUCT_ID_2,
                    name = PRODUCT_NAME_2,
                    price = PRODUCT_PRICE_2
                )
            ),
            promotions = listOf(
                BulkyItemsPromotionEntity(
                    id = "",
                    name = "",
                    productTargetId = PRODUCT_ID_1,
                    minimumQuantity = 0,
                    discountPercentagePerItem = 0
                ),
                BuyXGetYFreePromotionEntity(
                    id = "",
                    name = "",
                    productTargetId = PRODUCT_ID_2,
                    minimumQuantity = 0,
                    freeItemsQuantity = 0
                )
            )
        )

        val expectedOrder = Order(
            items = listOf(
                Order.Item(
                    productId = PRODUCT_ID_2,
                    productName = PRODUCT_NAME_2,
                    basePrice = PRODUCT_PRICE_2,
                    finalPrice = PRODUCT_PRICE_2,
                    promotionNameApplied = null
                )
            ),
            totalBasePrice = 23.0,
            totalFinalPrice = 23.0
        )

        Assert.assertEquals(expectedOrder, currentOrder)
    }

    @Test
    fun `GIVEN a cart item without promotion WHEN create order THEN then the default prices are applied`() {

    }
}