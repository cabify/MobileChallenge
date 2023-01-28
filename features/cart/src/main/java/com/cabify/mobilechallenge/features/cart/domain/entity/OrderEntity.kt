package com.cabify.mobilechallenge.features.cart.domain.entity

import com.cabify.shared.product.domain.entities.PromotionEntity

data class OrderEntity(
    val orderId: String,
    val items: List<Item>,
    val totalBasePrice: Double,
    val totalFinalPrice: Double
) {
    data class Item(
        val productId: String,
        val productName: String,
        val productImageUrl: String?,
        val unitBasePrice: Double,
        val unitFinalPrice: Double,
        val quantity: Int,
        val promotion: PromotionEntity? = null
    )
}