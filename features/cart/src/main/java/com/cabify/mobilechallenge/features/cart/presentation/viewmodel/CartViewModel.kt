package com.cabify.mobilechallenge.features.cart.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cabify.mobilechallenge.core.base.presentation.BaseViewModel
import com.cabify.mobilechallenge.features.cart.domain.usecase.GetOrderChangesUseCase
import com.cabify.mobilechallenge.features.cart.presentation.viewstate.CartViewState

class CartViewModel(private val getOrderChangesUseCase: GetOrderChangesUseCase) : BaseViewModel() {

    private val _viewState = MutableLiveData<CartViewState>()
    val viewState: LiveData<CartViewState> = _viewState

    init {
        addToDisposable(getOrderChangesUseCase().subscribe({

        }, {
            Log.e("getOrderChangesUseCase", it.stackTraceToString())
        }))
    }
}