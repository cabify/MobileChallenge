package com.cabify.mobilechallenge.shared.cart.data.mapper

import com.cabify.mobilechallenge.shared.cart.domain.entity.CartEntity
import com.cabify.mobilechallenge.core.base.mapper.Mapper
import com.cabify.mobilechallenge.persistence.entity.CartItemData

internal class CartItemDataMapper : Mapper<CartItemData, CartEntity.Item> {
    override fun map(input: CartItemData): CartEntity.Item =
        CartEntity.Item(productId = input.productId, quantity = input.quantity)
}