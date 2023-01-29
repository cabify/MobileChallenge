package com.cabify.mobilechallenge.persistence.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CartItemData(
    @PrimaryKey
    val productId: String,
    val quantity: Int
)