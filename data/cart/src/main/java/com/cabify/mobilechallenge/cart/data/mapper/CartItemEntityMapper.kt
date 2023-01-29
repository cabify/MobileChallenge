package com.cabify.mobilechallenge.cart.data.mapper

import com.cabify.mobilechallenge.cart.domain.entity.CartEntity
import com.cabify.mobilechallenge.core.base.mapper.Mapper
import com.cabify.mobilechallenge.persistence.entity.CartItemData

class CartItemEntityMapper : Mapper<CartEntity.Item, CartItemData> {
    override fun map(input: CartEntity.Item): CartItemData =
        CartItemData(productId = input.productId, quantity = input.quantity)
}