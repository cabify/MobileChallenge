package com.cabify.mobilechallenge.features.home.presentation.viewmodel

import androidx.lifecycle.Observer
import com.cabify.mobilechallenge.cart.domain.usecase.AddProductToCartUseCase
import com.cabify.mobilechallenge.features.home.presentation.mapper.ProductsPromotionsDomainToPresentationMapper
import com.cabify.mobilechallenge.features.home.presentation.model.ProductPresentation
import com.cabify.mobilechallenge.features.home.presentation.viewstate.HomeViewEvent
import com.cabify.mobilechallenge.features.home.presentation.viewstate.HomeViewState
import com.cabify.mobilechallenge.features.home.presentation.viewstate.Success
import com.cabify.shared.product.domain.entities.BulkyItemsPromotionEntity
import com.cabify.shared.product.domain.entities.BuyXGetYFreePromotionEntity
import com.cabify.shared.product.domain.entities.ProductEntity
import com.cabify.shared.product.domain.usecase.GetProductsUseCase
import com.cabify.shared.product.domain.usecase.GetPromotionsUseCase
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {
    private lateinit var homeViewModel: HomeViewModel

    @Mock
    private lateinit var getPromotionsUseCase: GetPromotionsUseCase

    @Mock
    private lateinit var getProductsUseCase: GetProductsUseCase

    @Mock
    private lateinit var addProductToCartUseCase: AddProductToCartUseCase

    @Mock
    private lateinit var productsPromotionsDomainToPresentationMapper: ProductsPromotionsDomainToPresentationMapper

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
        homeViewModel = HomeViewModel(
            getPromotionsUseCase = getPromotionsUseCase,
            getProductsUseCase = getProductsUseCase,
            addProductToCartUseCase = addProductToCartUseCase,
            productsPromotionsDomainToPresentationMapper = productsPromotionsDomainToPresentationMapper,
            subscribeScheduler = testScheduler,
            observerScheduler = testScheduler
        )
        homeViewModel.viewState.observeForever(viewStateObserver)
        homeViewModel.viewEvent.observeForever(viewEventObserver)
    }

    @Test
    fun `GIVEN getPromotionsUseCase succeed and getProductsUseCase succeed WHEN product list loaded THEN set Success view state`() {
        whenever(getProductsUseCase()) doReturn Single.just(anyProductEntities)
        whenever(getPromotionsUseCase()) doReturn Single.just(anyPromotionEntities)
        whenever(productsPromotionsDomainToPresentationMapper.map(any())) doReturn anyProductPresentations
        homeViewModel.loadProductList()
        verify(viewStateObserver).onChanged(Success(productPresentation = anyProductPresentations))
    }

    @Test
    fun `GIVEN getPromotionsUseCase started and getProductsUseCase started WHEN load product list starts THEN set Loading view state`() {

    }

    @Test
    fun `GIVEN getPromotionsUseCase failed WHEN load product list THEN set Error view state`() {

    }

    @Test
    fun `GIVEN getProductsUseCase failed WHEN load product list THEN set Error view state`() {

    }

    @Test
    fun `GIVEN product list loaded successfully WHEN add item to cart THEN trigger add product to cart success event `() {

    }
}