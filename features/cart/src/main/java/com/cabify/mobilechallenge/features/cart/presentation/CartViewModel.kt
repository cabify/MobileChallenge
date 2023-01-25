package com.cabify.mobilechallenge.features.cart.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cabify.mobilechallenge.core.base.presentation.BaseViewModel
import com.cabify.mobilechallenge.features.cart.domain.usecase.GetOrderChangesUseCase

class CartViewModel(private val getOrderChangesUseCase: GetOrderChangesUseCase) : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is cart Fragment"
    }
    val text: LiveData<String> = _text

    init {
        addToDisposable(getOrderChangesUseCase.invoke().subscribe({
            it
        }, {
            it
        }))
    }
}