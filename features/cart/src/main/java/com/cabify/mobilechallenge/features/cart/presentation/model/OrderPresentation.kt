package com.cabify.mobilechallenge.features.cart.presentation.model

sealed class OrderPresentation

data class OrderPricePresentation(
    val orderId: String,
    val totalPrice: String,
    val basePrice: String
) : OrderPresentation()

data class OrderItemPresentation(
    val productId: String,
    val productName: String,
    val quantity: String,
    val itemPrice: String,
    val subtotalPrice: String,
    val promotionName: String?,
    val promotionInfo: String?
) : OrderPresentation()
