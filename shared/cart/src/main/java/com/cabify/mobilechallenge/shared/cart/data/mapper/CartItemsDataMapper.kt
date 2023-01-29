package com.cabify.mobilechallenge.shared.cart.data.mapper

import com.cabify.mobilechallenge.shared.cart.domain.entity.CartEntity
import com.cabify.mobilechallenge.core.base.mapper.Mapper
import com.cabify.mobilechallenge.persistence.entity.CartItemData

internal class CartItemsDataMapper(private val cartItemDataMapper: Mapper<CartItemData, CartEntity.Item>) :
    Mapper<List<CartItemData>, CartEntity> {
    override fun map(input: List<CartItemData>): CartEntity =
        CartEntity(items = input.map(cartItemDataMapper::map))
}