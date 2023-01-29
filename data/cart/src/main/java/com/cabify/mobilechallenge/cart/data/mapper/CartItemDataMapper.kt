package com.cabify.mobilechallenge.cart.data.mapper

import com.cabify.mobilechallenge.cart.domain.entity.CartEntity
import com.cabify.mobilechallenge.core.base.mapper.Mapper
import com.cabify.mobilechallenge.persistence.entity.CartItemData

class CartItemDataMapper : Mapper<CartItemData, CartEntity.Item> {
    override fun map(input: CartItemData): CartEntity.Item =
        CartEntity.Item(productId = input.productId, quantity = input.quantity)
}