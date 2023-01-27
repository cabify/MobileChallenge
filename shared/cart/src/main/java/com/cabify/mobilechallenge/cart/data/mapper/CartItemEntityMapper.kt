package com.cabify.mobilechallenge.cart.data.mapper

import com.cabify.mobilechallenge.cart.domain.entity.CartEntity
import com.cabify.mobilechallenge.persistence.entity.CartItemData

class CartItemEntityMapper {

    fun map(cartItem: CartEntity.Item): CartItemData =
        CartItemData(productId = cartItem.productId, quantity = cartItem.quantity)
}