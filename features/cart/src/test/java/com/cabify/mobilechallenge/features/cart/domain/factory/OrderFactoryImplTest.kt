package com.cabify.mobilechallenge.features.cart.domain.factory

import com.cabify.mobilechallenge.shared.cart.domain.entity.CartEntity
import com.cabify.mobilechallenge.features.cart.domain.entity.OrderEntity
import com.cabify.mobilechallenge.features.cart.domain.processor.PromotionProcessor
import com.cabify.shared.product.domain.entities.BulkyItemsPromotionEntity
import com.cabify.shared.product.domain.entities.BuyXGetYFreePromotionEntity
import com.cabify.shared.product.domain.entities.ProductEntity
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class OrderFactoryImplTest {
    @Mock
    private lateinit var bulkyPromotionProcessor: PromotionProcessor

    @Mock
    private lateinit var buyXGetYFreePromotionProcessor: PromotionProcessor

    private lateinit var orderFactoryImpl: OrderFactoryImpl

    private val cartItem = CartEntity.Item(productId = PRODUCT_ID_1, quantity = 1)
    private val cartItems = listOf(cartItem)
    private val cartEntity = CartEntity(items = cartItems)

    @Before
    fun setup() {
        orderFactoryImpl = OrderFactoryImpl(
            promotionProcessors = mapOf(
                BulkyItemsPromotionEntity.APP_INTERNAL_ID to bulkyPromotionProcessor,
                BuyXGetYFreePromotionEntity.INTERNAL_ID to buyXGetYFreePromotionProcessor
            )
        )
    }


    private val productEntity1 = ProductEntity(
        id = PRODUCT_ID_1,
        name = PRODUCT_NAME_1,
        price = PRODUCT_PRICE_1,
        productImageUrl = ANY_PRODUCT_IMAGE_URL
    )

    private val productEntity2 = ProductEntity(
        id = PRODUCT_ID_2,
        name = PRODUCT_NAME_2,
        price = PRODUCT_PRICE_2,
        productImageUrl = ANY_PRODUCT_IMAGE_URL
    )

    private val bulkyItemsPromotionProductId1 = BulkyItemsPromotionEntity(
        id = "",
        name = "",
        productTargetId = PRODUCT_ID_1,
        minimumQuantity = 0,
        discountPercentagePerItem = 0
    )

    private val productEntity3 = ProductEntity(
        id = PRODUCT_ID_3,
        name = PRODUCT_NAME_3,
        price = PRODUCT_PRICE_3,
        productImageUrl = ANY_PRODUCT_IMAGE_URL
    )

    private val buyXGetYFreePromotionProductId2 = BuyXGetYFreePromotionEntity(
        id = "",
        name = "",
        productTargetId = PRODUCT_ID_2,
        minimumQuantity = 0,
        freeItemsQuantity = 0
    )


    private val cartItemProduct1 = cartItem.copy(productId = PRODUCT_ID_1)

    private val cartItemProduct2 = cartItem.copy(productId = PRODUCT_ID_2)

    private val cartItemProduct3 = cartItem.copy(productId = PRODUCT_ID_3)

    @Test
    fun `GIVEN the cart contains items WHEN create order THEN the order contains these items AND final prices`() {
        val orderEntityItemProduct1WithDiscount = OrderEntity.Item(
            productId = PRODUCT_ID_1,
            productName = PRODUCT_NAME_1,
            unitBasePrice = PRODUCT_PRICE_1,
            unitFinalPrice = PRODUCT_PRICE_1_WITH_DISCOUNT,
            promotion = anyPromotion1,
            quantity = 1,
            productImageUrl = ANY_PRODUCT_IMAGE_URL
        )
        val orderEntityItemProduct2WithDiscount = OrderEntity.Item(
            productId = PRODUCT_ID_2,
            productName = PRODUCT_NAME_2,
            unitBasePrice = PRODUCT_PRICE_2,
            unitFinalPrice = PRODUCT_PRICE_2_WITH_DISCOUNT,
            promotion = anyPromotion2,
            quantity = 1,
            productImageUrl = ANY_PRODUCT_IMAGE_URL
        )
        val orderEntityItemProduct3 = OrderEntity.Item(
            productId = PRODUCT_ID_3,
            productName = PRODUCT_NAME_3,
            unitBasePrice = PRODUCT_PRICE_3,
            unitFinalPrice = PRODUCT_PRICE_3,
            promotion = null,
            quantity = 1,
            productImageUrl = ANY_PRODUCT_IMAGE_URL
        )
        givenBulkyPromotionProcessorReturns(orderEntityItemProduct1WithDiscount)
        givenBuyXGetYFreePromotionProcessorReturns(orderEntityItemProduct2WithDiscount)

        val currentOrder = orderFactoryImpl.create(
            cart = cartEntity.copy(
                items = listOf(
                    cartItemProduct1,
                    cartItemProduct2,
                    cartItemProduct3
                )
            ),
            products = listOf(
                productEntity1,
                productEntity2,
                productEntity3
            ),
            promotions = listOf(
                bulkyItemsPromotionProductId1,
                buyXGetYFreePromotionProductId2
            )
        )
        val expectedOrderEntity = OrderEntity(
            items = listOf(
                orderEntityItemProduct1WithDiscount,
                orderEntityItemProduct2WithDiscount,
                orderEntityItemProduct3
            ),
            totalBasePrice = 63.0,
            totalFinalPrice = 60.0,
            orderId = OrderFactoryImpl.DEFAULT_ORDER_ID
        )

        Assert.assertEquals(expectedOrderEntity, currentOrder)
    }

    private fun givenBulkyPromotionProcessorReturns(orderEntityItemProduct1WithDiscount: OrderEntity.Item) {
        whenever(
            bulkyPromotionProcessor.process(
                any(),
                any(),
                any()
            )
        ) doReturn orderEntityItemProduct1WithDiscount
    }

    @Test
    fun `GIVEN the cart is empty WHEN create order THEN the order is created without items AND with zero prices`() {
        val currentOrder = orderFactoryImpl.create(
            cart = cartEntity.copy(
                items = listOf()
            ),
            products = listOf(
                productEntity1,
                productEntity2,
                productEntity3
            ),
            promotions = listOf(
                bulkyItemsPromotionProductId1,
                buyXGetYFreePromotionProductId2
            )
        )

        val expectedOrderEntity = OrderEntity(
            items = emptyList(),
            totalBasePrice = 0.0,
            totalFinalPrice = 0.0,
            orderId = OrderFactoryImpl.DEFAULT_ORDER_ID
        )

        Assert.assertEquals(expectedOrderEntity, currentOrder)
    }

    @Test
    fun `GIVEN the cart item does not correspond to any current available product WHEN create order THEN this item is not added in the order`() {
        val orderEntityItemProduct2WithDiscount = OrderEntity.Item(
            productId = PRODUCT_ID_2,
            productName = PRODUCT_NAME_2,
            unitBasePrice = PRODUCT_PRICE_2,
            unitFinalPrice = PRODUCT_PRICE_2_WITH_DISCOUNT,
            promotion = anyPromotion2,
            quantity = 1,
            productImageUrl = ANY_PRODUCT_IMAGE_URL
        )
        givenBuyXGetYFreePromotionProcessorReturns(orderEntityItemProduct2WithDiscount)

        val currentOrder = orderFactoryImpl.create(
            cart = cartEntity.copy(
                items = listOf(
                    cartItemProduct1,
                    cartItemProduct2
                )
            ),
            products = listOf(
                productEntity2
            ),
            promotions = listOf(
                bulkyItemsPromotionProductId1,
                buyXGetYFreePromotionProductId2
            )
        )

        val expectedOrderEntity = OrderEntity(
            items = listOf(
                orderEntityItemProduct2WithDiscount
            ),
            totalBasePrice = 23.0,
            totalFinalPrice = 22.0,
            orderId = OrderFactoryImpl.DEFAULT_ORDER_ID
        )

        Assert.assertEquals(expectedOrderEntity, currentOrder)
    }

    private fun givenBuyXGetYFreePromotionProcessorReturns(orderEntityItemProduct2WithDiscount: OrderEntity.Item) {
        whenever(
            buyXGetYFreePromotionProcessor.process(
                any(),
                any(),
                any()
            )
        ) doReturn orderEntityItemProduct2WithDiscount
    }

    @Test
    fun `GIVEN a cart item with a promotion associated BUT without promotion processor defined WHEN create order THEN the base prices are applied`() {

        val currentOrder = OrderFactoryImpl(
            promotionProcessors = mapOf()
        ).create(
            cart = cartEntity.copy(items = listOf(cartItemProduct1, cartItemProduct2)),
            products = listOf(productEntity2),
            promotions = listOf(
                bulkyItemsPromotionProductId1,
                buyXGetYFreePromotionProductId2
            )
        )

        val expectedOrderEntity = OrderEntity(
            items = listOf(
                OrderEntity.Item(
                    productId = PRODUCT_ID_2,
                    productName = PRODUCT_NAME_2,
                    unitBasePrice = PRODUCT_PRICE_2,
                    unitFinalPrice = PRODUCT_PRICE_2,
                    promotion = null,
                    quantity = 1,
                    productImageUrl = ANY_PRODUCT_IMAGE_URL
                )
            ),
            totalBasePrice = 23.0,
            totalFinalPrice = 23.0,
            orderId = OrderFactoryImpl.DEFAULT_ORDER_ID
        )

        Assert.assertEquals(expectedOrderEntity, currentOrder)
    }

    @Test
    fun `GIVEN a cart item without promotion WHEN create order THEN then the default prices are applied`() {
        val currentOrder = OrderFactoryImpl(
            promotionProcessors = mapOf()
        ).create(
            cart = cartEntity.copy(
                items = listOf(
                    cartItemProduct1,
                    cartItemProduct2
                )
            ),
            products = listOf(
                productEntity1,
                productEntity2
            ),
            promotions = listOf()
        )

        val expectedOrderEntity = OrderEntity(
            items = listOf(
                OrderEntity.Item(
                    productId = PRODUCT_ID_1,
                    productName = PRODUCT_NAME_1,
                    unitBasePrice = PRODUCT_PRICE_1,
                    unitFinalPrice = PRODUCT_PRICE_1,
                    promotion = null,
                    quantity = 1,
                    productImageUrl = ANY_PRODUCT_IMAGE_URL
                ),
                OrderEntity.Item(
                    productId = PRODUCT_ID_2,
                    productName = PRODUCT_NAME_2,
                    unitBasePrice = PRODUCT_PRICE_2,
                    unitFinalPrice = PRODUCT_PRICE_2,
                    promotion = null,
                    quantity = 1,
                    productImageUrl = ANY_PRODUCT_IMAGE_URL
                )
            ),
            totalBasePrice = 53.0,
            totalFinalPrice = 53.0,
            orderId = OrderFactoryImpl.DEFAULT_ORDER_ID
        )

        Assert.assertEquals(expectedOrderEntity, currentOrder)
    }

    private val anyPromotion1 = BulkyItemsPromotionEntity(
        id = "1",
        name = "",
        productTargetId = "",
        minimumQuantity = 0,
        discountPercentagePerItem = 0

    )
    private val anyPromotion2 = BuyXGetYFreePromotionEntity(
        id = "2", name = "", productTargetId = "", minimumQuantity = 0, freeItemsQuantity = 0
    )

    companion object {
        private const val PRODUCT_ID_1 = "1"
        private const val PRODUCT_ID_2 = "2"
        private const val PRODUCT_ID_3 = "3"

        private const val PRODUCT_NAME_1 = "1"
        private const val PRODUCT_NAME_2 = "2"
        private const val PRODUCT_NAME_3 = "3"

        private const val ANY_PRODUCT_IMAGE_URL ="any_pic"

        private const val PRODUCT_PRICE_1 = 30.0
        private const val PRODUCT_PRICE_1_WITH_DISCOUNT = 28.0
        private const val PRODUCT_PRICE_2 = 23.0
        private const val PRODUCT_PRICE_2_WITH_DISCOUNT = 22.0
        private const val PRODUCT_PRICE_3 = 10.0

    }
}