package com.cabify.mobilechallenge.shared.cart.domain.entity

data class CartEntity(val items: List<Item>) {
    data class Item(val productId: String, val quantity: Int)
}
