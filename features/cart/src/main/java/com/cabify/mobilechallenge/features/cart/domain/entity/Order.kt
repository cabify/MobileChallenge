package com.cabify.mobilechallenge.features.cart.domain.entity

data class Order(
    val items: List<Item>,
    val totalBasePrice: Double,
    val totalFinalPrice: Double
) {
    data class Item(
        val productId: String,
        val productName: String,
        val basePrice: Double,
        val finalPrice: Double,
        val promotionNameApplied: String? = null
    )
}