package com.cabify.demo.ui

import com.cabify.demo.data.model.Product

data class HomeUIState(
    val products: List<Product> = emptyList(),
    val loading: Boolean = false,
    val error: String? = null
)