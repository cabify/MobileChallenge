package com.cabify.mobilechallenge.features.cart.domain.entity

data class Order(
    val orderItems: List<OrderItem>,
    val finalTotalPrice: Double
)