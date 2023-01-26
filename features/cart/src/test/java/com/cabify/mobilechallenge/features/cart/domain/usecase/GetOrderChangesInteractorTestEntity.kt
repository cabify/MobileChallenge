package com.cabify.mobilechallenge.features.cart.domain.usecase

import com.cabify.mobilechallenge.cart.domain.entity.CartEntity
import com.cabify.mobilechallenge.cart.domain.repository.CartRepository
import com.cabify.mobilechallenge.features.cart.domain.entity.OrderEntity
import com.cabify.mobilechallenge.features.cart.domain.factory.OrderFactory
import com.cabify.shared.product.domain.entities.BulkyItemsPromotionEntity
import com.cabify.shared.product.domain.entities.ProductEntity
import com.cabify.shared.product.domain.repository.ProductsRepository
import com.cabify.shared.product.domain.repository.PromotionsRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class GetOrderChangesInteractorTestEntity {
    private lateinit var getOrderChangesInteractor: GetOrderChangesInteractor

    @Mock
    private lateinit var cartRepository: CartRepository

    @Mock
    private lateinit var productRepository: ProductsRepository

    @Mock
    private lateinit var promotionsRepository: PromotionsRepository

    @Mock
    private lateinit var orderFactory: OrderFactory


    @Before
    fun setup() {
        getOrderChangesInteractor = GetOrderChangesInteractor(
            cartRepository,
            productRepository,
            promotionsRepository,
            orderFactory
        )
    }

    @Test
    fun `GIVEN there is a cart update WHEN getOrderChanges THEN the order is updated`() {
        givenGetProductsSucceed()
        givenGetPromotionsSucceed()
        givenOrderFactoryCreation()
        givenCartUpdate()

        getOrderChangesInteractor()
            .test()
            .assertValue(anyOrderEntity)
    }

    private fun givenGetProductsSucceed() {
        whenever(productRepository.getProducts()) doReturn Single.just(listOf(anyProduct))
    }

    private fun givenGetPromotionsSucceed() {
        whenever(promotionsRepository.getPromotions()) doReturn Single.just(listOf(anyPromotion))
    }

    private fun givenOrderFactoryCreation() {
        whenever(orderFactory.create(any(), any(), any())) doReturn anyOrderEntity
    }

    private fun givenCartUpdate() {
        whenever(cartRepository.cartChanges()) doReturn Observable.just(anyCartEntity)
    }

    private val anyProduct = ProductEntity(
        id = "", name = "", price = 0.0
    )

    private val anyCartEntity = CartEntity(
        items = listOf(
            CartEntity.Item(
                productId = anyProduct.id, quantity = 1
            )
        )
    )
    private val anyPromotion = BulkyItemsPromotionEntity(
        id = "", name = "", productTargetId = "", minimumQuantity = 0, discountPercentagePerItem = 0
    )


    private val anyOrderEntity = OrderEntity(
        items = listOf(), totalBasePrice = 0.0, totalFinalPrice = 0.0
    )
}