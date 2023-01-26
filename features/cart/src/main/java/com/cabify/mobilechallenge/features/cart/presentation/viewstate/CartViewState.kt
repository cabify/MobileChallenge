package com.cabify.mobilechallenge.features.cart.presentation.viewstate

import com.cabify.mobilechallenge.features.cart.presentation.OrderPresentation

sealed class CartViewState

data class Success(val order: OrderPresentation) : CartViewState() {
    val isEmpty: Boolean
        get() = order.items.isEmpty()
}

data class Error(val throwable: Throwable) : CartViewState()
object Loading : CartViewState()

