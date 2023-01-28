package com.cabify.mobilechallenge.features.cart.presentation.mapper

import com.cabify.mobilechallenge.features.cart.domain.entity.OrderEntity
import com.cabify.mobilechallenge.features.cart.presentation.model.PromotionPresentation

interface OrderEntityToPromotionPresentationMapper {
    fun map(orderItem: OrderEntity.Item): PromotionPresentation
}
