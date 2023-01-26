package com.cabify.mobilechallenge.features.cart.presentation

data class OrderPresentation(
    val items: List<Item>,
    val totalBasePrice: String,
    val totalFinalPrice: String
) {
    data class Item(
        val productId: String,
        val productName: String,
        val basePrice: String,
        val finalPrice: String,
        val promotionNameApplied: String? = null
    )
}