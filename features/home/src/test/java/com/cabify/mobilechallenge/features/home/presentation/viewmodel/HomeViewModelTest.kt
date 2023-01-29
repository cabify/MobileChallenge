package com.cabify.mobilechallenge.features.home.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.cabify.mobilechallenge.cart.domain.usecase.AddProductToCartUseCase
import com.cabify.mobilechallenge.core.base.mapper.Mapper
import com.cabify.mobilechallenge.features.home.presentation.model.ProductPresentation
import com.cabify.mobilechallenge.features.home.presentation.model.ProductsPromotions
import com.cabify.mobilechallenge.features.home.presentation.viewstate.AddProductToCartSucceed
import com.cabify.mobilechallenge.features.home.presentation.viewstate.Error
import com.cabify.mobilechallenge.features.home.presentation.viewstate.ErrorEvent
import com.cabify.mobilechallenge.features.home.presentation.viewstate.HomeViewEvent
import com.cabify.mobilechallenge.features.home.presentation.viewstate.HomeViewState
import com.cabify.mobilechallenge.features.home.presentation.viewstate.Loading
import com.cabify.mobilechallenge.features.home.presentation.viewstate.Success
import com.cabify.shared.product.domain.entities.BulkyItemsPromotionEntity
import com.cabify.shared.product.domain.entities.BuyXGetYFreePromotionEntity
import com.cabify.shared.product.domain.entities.ProductEntity
import com.cabify.shared.product.domain.usecase.GetProductsUseCase
import com.cabify.shared.product.domain.usecase.GetPromotionsUseCase
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var homeViewModel: HomeViewModel

    @Mock
    private lateinit var getPromotionsUseCase: GetPromotionsUseCase

    @Mock
    private lateinit var getProductsUseCase: GetProductsUseCase

    @Mock
    private lateinit var addProductToCartUseCase: AddProductToCartUseCase

    @Mock
    private lateinit var productsPromotionsDomainToPresentationMapper: Mapper<ProductsPromotions, List<ProductPresentation>>

    @Mock
    private lateinit var viewStateObserver: Observer<HomeViewState>

    @Mock
    private lateinit var viewEventObserver: Observer<HomeViewEvent>

    private val testScheduler = Schedulers.trampoline()

    private val anyProduct = ProductEntity(
        id = "", name = "", price = 0.0, productImageUrl = null
    )

    private val anyProductEntities = listOf(
        anyProduct,
        anyProduct,
        anyProduct
    )

    private val anyPromotionEntities = listOf(
        BulkyItemsPromotionEntity(
            id = "",
            name = "",
            productTargetId = "",
            minimumQuantity = 0,
            discountPercentagePerItem = 0
        ),
        BuyXGetYFreePromotionEntity(
            id = "",
            name = "",
            productTargetId = "",
            minimumQuantity = 0,
            freeItemsQuantity = 0

        )
    )

    private val anyThrowable = Throwable("Test error")

    private val anyProductPresentation = ProductPresentation(
        id = "",
        name = "",
        price = "",
        productImageUrl = null,
        availablePromotionName = null
    )
    private val anyProductPresentations = listOf(
        anyProductPresentation,
        anyProductPresentation
    )

    @Before
    fun setup() {
        setupViewModel()
        setupObservers()
    }

    private fun setupViewModel() {
        homeViewModel = HomeViewModel(
            getPromotionsUseCase = getPromotionsUseCase,
            getProductsUseCase = getProductsUseCase,
            addProductToCartUseCase = addProductToCartUseCase,
            productsPromotionsDomainToPresentationMapper = productsPromotionsDomainToPresentationMapper,
            subscribeScheduler = testScheduler,
            observerScheduler = testScheduler
        )
    }

    private fun setupObservers() {
        homeViewModel.viewState.observeForever(viewStateObserver)
        homeViewModel.viewEvent.observeForever(viewEventObserver)
    }

    @Test
    fun `GIVEN getPromotionsUseCase succeed and getProductsUseCase succeed WHEN product list loaded THEN set Success view state`() {
        givenGetProductsSucceed()
        givenGetPromotionsSucceed()
        givenProductsPromotionsDomainMocked()
        whenLoadProductList()
        thenSetSuccessViewState()
    }

    private fun thenSetSuccessViewState() {
        argumentCaptor {
            verify(viewStateObserver, times(2)).onChanged(capture())
            assertEquals(secondValue, Success(productPresentation = anyProductPresentations))
        }
    }

    @Test
    fun `GIVEN getPromotionsUseCase started and getProductsUseCase started WHEN load product list starts THEN set Loading view state`() {
        givenGetProductsSucceed()
        givenGetPromotionsSucceed()
        givenProductsPromotionsDomainMocked()
        whenLoadProductList()
        thenSetLoadingViewState()

    }

    private fun thenSetLoadingViewState() {
        argumentCaptor {
            verify(viewStateObserver, times(2)).onChanged(capture())
            assertEquals(firstValue, Loading)
        }
    }

    @Test
    fun `GIVEN getPromotionsUseCase failed WHEN load product list THEN set Error view state`() {
        givenGetProductsSucceed()
        givenGetPromotionsFailed()
        whenLoadProductList()
        thenSetErrorViewState()
    }

    private fun givenGetPromotionsFailed() {
        whenever(getPromotionsUseCase()) doReturn Single.error(anyThrowable)
    }

    @Test
    fun `GIVEN getProductsUseCase failed WHEN load product list THEN set Error view state`() {
        givenGetProductsFailed()
        givenGetPromotionsSucceed()
        whenLoadProductList()
        thenSetErrorViewState()
    }

    private fun givenGetProductsFailed() {
        whenever(getProductsUseCase()) doReturn Single.error(anyThrowable)
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

    @Test
    fun `GIVEN product list loaded successfully AND add product succeed WHEN add item to cart THEN trigger add product to cart success event `() {
        givenProductListLoadedSuccessfully()
        givenAddProductToCartSucceed()
        whenAddProductToCart()
        thenTriggerAddProductToCartSucceedEvent()
    }

    private fun thenTriggerAddProductToCartSucceedEvent() {
        verify(viewEventObserver).onChanged(AddProductToCartSucceed)
    }

    private fun givenAddProductToCartSucceed() {
        whenever(addProductToCartUseCase(anyString(), anyInt())) doReturn Completable.complete()
    }

    @Test
    fun `GIVEN product list loaded successfully AND add product failed  WHEN add item to cart THEN trigger add product to cart error event `() {
        givenProductListLoadedSuccessfully()
        whenAddProductToCart()
        thenTriggerAddProductToCartErrorEvent()
    }

    private fun givenProductListLoadedSuccessfully() {
        givenGetProductsSucceed()
        givenGetPromotionsSucceed()
        givenProductsPromotionsDomainMocked()
        givenAddProductToCartFailed()
        whenLoadProductList()
    }

    private fun thenTriggerAddProductToCartErrorEvent() {
        verify(viewEventObserver).onChanged(ErrorEvent(anyThrowable))
    }

    private fun whenAddProductToCart() {
        homeViewModel.addProductToCart(ANY_PRODUCT_ID, ANY_QUANTITY)
    }

    private fun givenAddProductToCartFailed() {
        whenever(addProductToCartUseCase(anyString(), anyInt())) doReturn Completable.error(
            anyThrowable
        )
    }

    private fun whenLoadProductList() {
        homeViewModel.loadProductList()
    }

    private fun givenProductsPromotionsDomainMocked() {
        whenever(productsPromotionsDomainToPresentationMapper.map(any())) doReturn anyProductPresentations
    }

    private fun givenGetPromotionsSucceed() {
        whenever(getPromotionsUseCase()) doReturn Single.just(anyPromotionEntities)
    }

    private fun givenGetProductsSucceed() {
        whenever(getProductsUseCase()) doReturn Single.just(anyProductEntities)
    }

    companion object {
        private const val ANY_PRODUCT_ID = "21"
        private const val ANY_QUANTITY = 2
    }
}