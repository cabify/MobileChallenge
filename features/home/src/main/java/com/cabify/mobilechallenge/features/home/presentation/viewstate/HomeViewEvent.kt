package com.cabify.mobilechallenge.features.home.presentation.viewstate

import com.cabify.mobilechallenge.features.home.presentation.model.ProductPresentation

sealed class HomeViewState

data class Success(val productPresentation: List<ProductPresentation>) : HomeViewState() {
    val isEmpty
        get() = productPresentation.isEmpty()
}

data class Error(val throwable: Throwable) : HomeViewState()
object Loading : HomeViewState()
