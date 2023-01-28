package com.cabify.mobilechallenge.app.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cabify.mobilechallenge.cart.domain.usecase.GetCartItemsQuantityUseCase
import com.cabify.mobilechallenge.core.base.presentation.BaseViewModel
import io.reactivex.rxjava3.core.Scheduler

class MainViewModel(
    getCartItemsQuantityUseCase: GetCartItemsQuantityUseCase,
    subscribeScheduler: Scheduler,
    observerScheduler: Scheduler
) :
    BaseViewModel() {
    private val _cartBadgesQuantity: MutableLiveData<Int> = MutableLiveData()
    val cartItemsQuantity: LiveData<Int> = _cartBadgesQuantity

    init {
        addToDisposable(
            getCartItemsQuantityUseCase()
                .subscribeOn(subscribeScheduler)
                .observeOn(observerScheduler)
                .subscribe({
                    _cartBadgesQuantity.value = it
                }, {
                    _cartBadgesQuantity.value = 0
                })
        )
    }
}