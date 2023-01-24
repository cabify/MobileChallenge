package com.cabify.mobilechallenge.features.home.presentation.viewstate

sealed class HomeViewEvent
object AddProductToCartSucceed : HomeViewEvent()
data class ErrorEvent(val throwable: Throwable) : HomeViewEvent()