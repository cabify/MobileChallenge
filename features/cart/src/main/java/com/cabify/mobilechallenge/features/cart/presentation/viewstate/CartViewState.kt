package com.cabify.mobilechallenge.features.cart.presentation.viewstate

import com.cabify.mobilechallenge.features.cart.presentation.model.OrderPresentation

sealed class CartViewState

data class Success(val orderPresentations: List<OrderPresentation>) : CartViewState() {
    val isEmpty: Boolean
        get() = orderPresentations.isEmpty()
}

data class Error(val throwable: Throwable) : CartViewState()
object Loading : CartViewState()

