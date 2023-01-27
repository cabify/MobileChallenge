package com.cabify.mobilechallenge.features.home.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cabify.library.utils.lifecycle.SingleLiveEvent
import com.cabify.mobilechallenge.cart.domain.entity.CartEntity
import com.cabify.mobilechallenge.cart.domain.usecase.AddProductToCartUseCase
import com.cabify.mobilechallenge.core.base.presentation.BaseViewModel
import com.cabify.mobilechallenge.features.home.presentation.mapper.ProductsPromotionsDomainToPresentationMapper
import com.cabify.mobilechallenge.features.home.presentation.model.ProductsPromotions
import com.cabify.mobilechallenge.features.home.presentation.viewstate.AddProductToCartSucceed
import com.cabify.mobilechallenge.features.home.presentation.viewstate.HomeViewState
import com.cabify.mobilechallenge.features.home.presentation.viewstate.Loading
import com.cabify.mobilechallenge.features.home.presentation.viewstate.Success
import com.cabify.shared.product.domain.usecase.GetProductsUseCase
import com.cabify.shared.product.domain.usecase.GetPromotionsUseCase
import com.cabify.mobilechallenge.features.home.presentation.viewstate.Error
import com.cabify.mobilechallenge.features.home.presentation.viewstate.ErrorEvent
import com.cabify.mobilechallenge.features.home.presentation.viewstate.HomeViewEvent
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single

class HomeViewModel(
    private val getPromotionsUseCase: GetPromotionsUseCase,
    private val getProductsUseCase: GetProductsUseCase,
    private val addProductToCartUseCase: AddProductToCartUseCase,
    private val productsPromotionsDomainToPresentationMapper: ProductsPromotionsDomainToPresentationMapper,
    private val subscribeScheduler: Scheduler,
    private val observerScheduler: Scheduler
) : BaseViewModel() {

    private val _viewState = MutableLiveData<HomeViewState>()
    val viewState: LiveData<HomeViewState> = _viewState

    private val _viewEvent = SingleLiveEvent<HomeViewEvent>()
    val viewEvent: LiveData<HomeViewEvent> = _viewEvent

    fun loadProductList() {
        addToDisposable(
            Single.zip(
                getProductsUseCase(),
                getPromotionsUseCase()
            ) { products, promotions -> ProductsPromotions(products, promotions) }
                .map(::mapToViewState)
                .toObservable()
                .startWithItem(Loading)
                .subscribeOn(subscribeScheduler)
                .observeOn(observerScheduler)
                .subscribe({
                    _viewState.value = it
                }, {
                    _viewState.value = Error(it)
                })
        )
    }

    private fun mapToViewState(productsPromotions: ProductsPromotions): HomeViewState =
        Success(
            productPresentation = productsPromotionsDomainToPresentationMapper.map(
                productsPromotions
            )
        )

    fun addProductToCart(productId: String) {
        addToDisposable(
            addProductToCartUseCase(
                CartEntity.Item(productId = productId, quantity = 1)
            )
                .subscribeOn(subscribeScheduler)
                .observeOn(observerScheduler)
                .subscribe({
                    _viewEvent.value = AddProductToCartSucceed
                }, {
                    _viewEvent.value = ErrorEvent(it)
                })
        )
    }
}