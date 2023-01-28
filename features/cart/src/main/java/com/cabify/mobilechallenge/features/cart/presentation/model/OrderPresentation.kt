package com.cabify.mobilechallenge.features.cart.presentation.model

sealed class OrderPresentation

data class OrderPricePresentation(
    val orderId: String,
    val totalPrice: String,
    val baseTotalPrice: String,
    val promotionDiscountedPrice:String
) : OrderPresentation()

data class OrderItemPresentation(
    val productId: String,
    val productName: String,
    val quantity: String,
    val itemPrice: String,
    val subtotalPrice: String,
    val promotionPresentation: PromotionPresentation?
) : OrderPresentation()
