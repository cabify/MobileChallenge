package com.cabify.mobilechallenge.features.cart.domain.entity

data class OrderItem(
    val productId: String,
    val productName: String,
    val quantity: Int,
    val promotionName: String? = null,
    val totalBasePrice: Double,
    val totalFinalPrice: Double
)