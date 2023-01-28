package com.cabify.mobilechallenge.features.cart.presentation.viewstate

import com.cabify.mobilechallenge.features.cart.presentation.model.OrderPresentation
import com.cabify.mobilechallenge.features.cart.presentation.model.OrderPricePresentation

sealed class CartViewState

data class Success(val data: List<OrderPresentation>) : CartViewState() {
    val isEmpty: Boolean
        get() = data.isEmpty()
}

data class Error(val throwable: Throwable) : CartViewState()
object Loading : CartViewState()

