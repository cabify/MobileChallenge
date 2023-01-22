package com.cabify.mobilechallenge.features.cart.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cabify.core.base.presentation.BaseViewModel

class CartViewModel : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is cart Fragment"
    }
    val text: LiveData<String> = _text
}