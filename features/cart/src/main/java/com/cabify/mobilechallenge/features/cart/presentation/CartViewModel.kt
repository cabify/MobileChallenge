package com.cabify.mobilechallenge.features.cart.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cabify.mobilechallenge.core.base.presentation.BaseViewModel
import com.cabify.mobilechallenge.features.cart.domain.usecase.GetOrderUseCase

class CartViewModel(private val getOrderUseCase: GetOrderUseCase) : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is cart Fragment"
    }
    val text: LiveData<String> = _text

    init {
        addToDisposable(getOrderUseCase.invoke().subscribe({
            it
        }, {
            it
        }))
    }
}