package com.cabify.mobilechallenge.cart.data.mapper

import com.cabify.mobilechallenge.cart.domain.entity.CartEntity
import com.cabify.mobilechallenge.persistence.entity.CartItemData

class CartItemDataMapper {
    fun map(cartItemData: List<CartItemData>): CartEntity =
        CartEntity(items = cartItemData.map(::map))

    fun map(cartItemData: CartItemData): CartEntity.Item =
        CartEntity.Item(productId = cartItemData.productId, quantity = cartItemData.quantity)
}