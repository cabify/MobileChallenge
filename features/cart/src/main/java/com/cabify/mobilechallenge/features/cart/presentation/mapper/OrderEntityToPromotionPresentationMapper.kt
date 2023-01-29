package com.cabify.mobilechallenge.features.cart.presentation.mapper

import com.cabify.mobilechallenge.core.base.mapper.Mapper
import com.cabify.mobilechallenge.features.cart.domain.entity.OrderEntity
import com.cabify.mobilechallenge.features.cart.presentation.model.PromotionPresentation

interface OrderEntityToPromotionPresentationMapper :
    Mapper<OrderEntity.Item, PromotionPresentation> {
    override fun map(input: OrderEntity.Item): PromotionPresentation
}
