package com.cabify.mobilechallenge.cart.data.mapper

import com.cabify.mobilechallenge.cart.domain.entity.CartEntity
import com.cabify.mobilechallenge.core.base.mapper.Mapper
import com.cabify.mobilechallenge.persistence.entity.CartItemData

class CartItemsDataMapper(private val cartItemDataMapper: CartItemDataMapper) :
    Mapper<List<CartItemData>, CartEntity> {
    override fun map(input: List<CartItemData>): CartEntity =
        CartEntity(items = input.map(cartItemDataMapper::map))
}