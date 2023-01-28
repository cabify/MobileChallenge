package com.cabify.shared.product.domain.entities

data class ProductEntity(
    val id: String,
    val name: String,
    val price: Double,
    val productImageUrl: String?
)