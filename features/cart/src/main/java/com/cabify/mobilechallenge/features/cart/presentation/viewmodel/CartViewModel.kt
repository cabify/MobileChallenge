package com.cabify.mobilechallenge.features.cart.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cabify.library.utils.lifecycle.SingleLiveEvent
import com.cabify.mobilechallenge.core.base.presentation.BaseViewModel
import com.cabify.mobilechallenge.features.cart.domain.entity.OrderEntity
import com.cabify.mobilechallenge.features.cart.domain.usecase.CheckoutOrderUseCase
import com.cabify.mobilechallenge.features.cart.domain.usecase.GetOrderChangesUseCase
import com.cabify.mobilechallenge.features.cart.presentation.mapper.OrderEntityToPresentationMapper
import com.cabify.mobilechallenge.features.cart.presentation.viewstate.CartViewEvent
import com.cabify.mobilechallenge.features.cart.presentation.viewstate.CartViewState
import com.cabify.mobilechallenge.features.cart.presentation.viewstate.CheckoutSucceed
import com.cabify.mobilechallenge.features.cart.presentation.viewstate.CheckoutFailed
import com.cabify.mobilechallenge.features.cart.presentation.viewstate.Loading
import com.cabify.mobilechallenge.features.cart.presentation.viewstate.Error
import com.cabify.mobilechallenge.features.cart.presentation.viewstate.Success
import io.reactivex.rxjava3.core.Scheduler

class CartViewModel(
    private val getOrderChangesUseCase: GetOrderChangesUseCase,
    private val orderEntityToPresentationMapper: OrderEntityToPresentationMapper,
    private val checkoutOrderUseCase: CheckoutOrderUseCase,
    private val subscribeScheduler: Scheduler,
    private val observerScheduler: Scheduler
) : BaseViewModel() {

    private val _viewState = MutableLiveData<CartViewState>()
    val viewState: LiveData<CartViewState> = _viewState

    private val _viewEvent = SingleLiveEvent<CartViewEvent>()
    val viewEvent: LiveData<CartViewEvent> = _viewEvent

    init {
        addToDisposable(
            getOrderChangesUseCase()
                .subscribeOn(subscribeScheduler)
                .observeOn(observerScheduler)
                .map(::mapToViewState)
                .startWithItem(Loading)
                .subscribe({ successViewState ->
                    _viewState.value = successViewState
                }, { throwable ->
                    _viewState.value = Error(throwable)
                })
        )
    }

    private fun mapToViewState(orderEntity: OrderEntity): CartViewState {
        val orderPresentations = orderEntityToPresentationMapper.map(orderEntity)
        return Success(orderPresentations)
    }

    fun checkoutOrder() {
        addToDisposable(
            checkoutOrderUseCase()
                .subscribeOn(subscribeScheduler)
                .observeOn(observerScheduler)
                .subscribe(
                    {
                        _viewEvent.value = CheckoutSucceed
                    },
                    {
                        _viewEvent.value = CheckoutFailed
                    }
                )
        )
    }
}