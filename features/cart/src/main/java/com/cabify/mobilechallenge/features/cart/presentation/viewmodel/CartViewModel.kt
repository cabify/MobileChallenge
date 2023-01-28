package com.cabify.mobilechallenge.features.cart.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cabify.mobilechallenge.core.base.presentation.BaseViewModel
import com.cabify.mobilechallenge.features.cart.domain.entity.OrderEntity
import com.cabify.mobilechallenge.features.cart.domain.usecase.GetOrderChangesUseCase
import com.cabify.mobilechallenge.features.cart.presentation.mapper.OrderEntityToPresentationMapper
import com.cabify.mobilechallenge.features.cart.presentation.viewstate.CartViewState
import com.cabify.mobilechallenge.features.cart.presentation.viewstate.Loading
import com.cabify.mobilechallenge.features.cart.presentation.viewstate.Error
import com.cabify.mobilechallenge.features.cart.presentation.viewstate.Success
import io.reactivex.rxjava3.core.Scheduler

class CartViewModel(
    private val getOrderChangesUseCase: GetOrderChangesUseCase,
    private val orderEntityToPresentationMapper: OrderEntityToPresentationMapper,
    private val subscribeScheduler: Scheduler,
    private val observerScheduler: Scheduler
) : BaseViewModel() {

    private val _viewState = MutableLiveData<CartViewState>()
    val viewState: LiveData<CartViewState> = _viewState

    init {
        addToDisposable(
            getOrderChangesUseCase()
                .subscribeOn(subscribeScheduler)
                .observeOn(observerScheduler)
                .map(::mapToViewState)
                .startWithItem(Loading)
                .subscribeOn(subscribeScheduler)
                .observeOn(observerScheduler)
                .subscribe({ successViewState ->
                    _viewState.value = successViewState
                }, { throwable ->
                    _viewState.value = Error(throwable)
                })
        )
    }

    private fun mapToViewState(orderEntity: OrderEntity): CartViewState =
        Success(orderEntityToPresentationMapper.map(orderEntity))
}