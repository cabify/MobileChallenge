package com.cabify.mobilechallenge.features.cart.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.cabify.mobilechallenge.core.base.mapper.Mapper
import com.cabify.mobilechallenge.features.cart.domain.entity.OrderEntity
import com.cabify.mobilechallenge.features.cart.domain.usecase.CheckoutOrderUseCase
import com.cabify.mobilechallenge.features.cart.domain.usecase.GetOrderChangesUseCase
import com.cabify.mobilechallenge.features.cart.presentation.model.OrderItemPresentation
import com.cabify.mobilechallenge.features.cart.presentation.model.OrderPresentation
import com.cabify.mobilechallenge.features.cart.presentation.model.OrderPricePresentation
import com.cabify.mobilechallenge.features.cart.presentation.viewstate.CartViewEvent
import com.cabify.mobilechallenge.features.cart.presentation.viewstate.CartViewState
import com.cabify.mobilechallenge.features.cart.presentation.viewstate.CheckoutFailed
import com.cabify.mobilechallenge.features.cart.presentation.viewstate.CheckoutSucceed
import com.cabify.mobilechallenge.features.cart.presentation.viewstate.Error
import com.cabify.mobilechallenge.features.cart.presentation.viewstate.Loading
import com.cabify.mobilechallenge.features.cart.presentation.viewstate.Success
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class CartViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var getOrderChangesUseCase: GetOrderChangesUseCase

    @Mock
    private lateinit var checkoutOrderUseCase: CheckoutOrderUseCase

    @Mock
    private lateinit var orderEntityToPresentationMapper: Mapper<OrderEntity, List<OrderPresentation>>

    @Mock
    private lateinit var viewStateObserver: Observer<CartViewState>

    @Mock
    private lateinit var viewEventObserver: Observer<CartViewEvent>

    private lateinit var cartViewModel: CartViewModel

    private val testScheduler = Schedulers.trampoline()

    private lateinit var publishSubject: PublishSubject<OrderEntity>

    private val anyOrderItemPresentation = OrderItemPresentation(
        productId = "",
        productName = "",
        productImageUrl = null,
        quantity = "",
        unitBasePrice = "",
        unitFinalPrice = "",
        finalSubtotalPrice = "",
        baseSubtotalPrice = "",
        promotionPresentation = null
    )
    private val anyOrderPricePresentation = OrderPricePresentation(
        orderId = "",
        totalPrice = "",
        baseTotalPrice = "",
        promotionDiscountedPrice = ""
    )
    private val orderPresentations: List<OrderPresentation> = listOf(
        anyOrderItemPresentation,
        anyOrderPricePresentation
    )

    private val anyOrderEntity = OrderEntity(
        orderId = "",
        items = listOf(),
        totalBasePrice = 0.0,
        totalFinalPrice = 0.0
    )

    private val anyThrowable = Throwable("Test error")

    @Before
    fun setup() {
        setupPublishSubject()
        setupViewModel()
        setupObservers()
    }

    private fun setupViewModel() {
        cartViewModel = CartViewModel(
            getOrderChangesUseCase = getOrderChangesUseCase,
            checkoutOrderUseCase = checkoutOrderUseCase,
            orderEntityToPresentationMapper = orderEntityToPresentationMapper,
            subscribeScheduler = testScheduler,
            observerScheduler = testScheduler
        )
    }

    private fun setupObservers() {
        cartViewModel.viewState.observeForever(viewStateObserver)
        cartViewModel.viewEvent.observeForever(viewEventObserver)
    }

    private fun setupPublishSubject() {
        publishSubject = PublishSubject.create()
        whenever(getOrderChangesUseCase()) doReturn publishSubject
    }

    @Test
    fun `GIVEN there is a subscription to getOrderChangesUseCase succeeding WHEN there is a new order change THEN set a new success order view state`() {
        givenOrderEntityToPresentationSucceed()
        whenOrderSucceedUpdate()
        thenSetSuccessViewState()
    }

    private fun thenSetSuccessViewState() {
        argumentCaptor {
            verify(viewStateObserver, times(2)).onChanged(capture())
            assertEquals(secondValue, Success(orderPresentations))
        }
    }

    @Test
    fun `GIVEN there is a subscription to getOrderChangesUseCase failing WHEN there is a new order change THEN set a new error order view state`() {
        whenOrderFailUpdate()
        thenSetErrorViewState()
    }

    private fun thenSetErrorViewState() {
        argumentCaptor {
            verify(viewStateObserver, times(2)).onChanged(capture())
            assertEquals(
                secondValue,
                Error(anyThrowable)
            )
        }
    }

    private fun whenOrderFailUpdate() {
        publishSubject.onError(anyThrowable)
    }

    @Test
    fun `GIVEN there is a new subscription to getOrderChangesUseCase failing WHEN subscription starts THEN set a new Loading order view state`() {
        givenOrderEntityToPresentationSucceed()
        whenOrderSucceedUpdate()
        thenSetLoadingViewState()
    }

    private fun thenSetLoadingViewState() {
        argumentCaptor {
            verify(viewStateObserver, times(2)).onChanged(capture())
            assertEquals(firstValue, Loading)
        }
    }

    private fun whenOrderSucceedUpdate() {
        publishSubject.onNext(anyOrderEntity)
    }

    private fun givenOrderEntityToPresentationSucceed() {
        whenever(orderEntityToPresentationMapper.map(any())) doReturn orderPresentations
    }

    @Test
    fun `GIVEN checkout order succeed WHEN checkout the order THEN trigger CheckoutSucceed event`() {
        givenCheckoutOrderSucceed()
        whenCheckoutOrder()
        thenTriggerCheckoutSucceedEvent()
    }

    private fun thenTriggerCheckoutSucceedEvent() {
        verify(viewEventObserver).onChanged(CheckoutSucceed)
    }

    private fun givenCheckoutOrderSucceed() {
        whenever(checkoutOrderUseCase()) doReturn Completable.complete()
    }

    @Test
    fun `GIVEN checkout order failed WHEN checkout the order THEN trigger CheckoutFailed event`() {
        givenCheckoutOrderFailed()
        whenCheckoutOrder()
        thenTriggerCheckoutFailed()
    }

    private fun thenTriggerCheckoutFailed() {
        verify(viewEventObserver).onChanged(CheckoutFailed)
    }

    private fun givenCheckoutOrderFailed() {
        whenever(checkoutOrderUseCase()) doReturn Completable.error(anyThrowable)
    }

    private fun whenCheckoutOrder() {
        cartViewModel.checkoutOrder()
    }
}