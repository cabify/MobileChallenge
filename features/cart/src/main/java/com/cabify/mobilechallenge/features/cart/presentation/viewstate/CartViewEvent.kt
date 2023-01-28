package com.cabify.mobilechallenge.features.cart.presentation.viewstate

sealed class CartViewEvent

object CheckoutSucceed: CartViewEvent()
object CheckoutFailed: CartViewEvent()
