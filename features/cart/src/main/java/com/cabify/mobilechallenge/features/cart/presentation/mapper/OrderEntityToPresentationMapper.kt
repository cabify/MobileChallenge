package com.cabify.mobilechallenge.features.cart.presentation.mapper

import com.cabify.library.utils.CurrencyUtils
import com.cabify.mobilechallenge.features.cart.domain.entity.OrderEntity
import com.cabify.mobilechallenge.features.cart.presentation.model.OrderItemPresentation
import com.cabify.mobilechallenge.features.cart.presentation.model.OrderPresentation
import com.cabify.mobilechallenge.features.cart.presentation.model.OrderPricePresentation
import com.cabify.mobilechallenge.features.cart.presentation.model.PromotionPresentation

class OrderEntityToPresentationMapper(
    private val currencyUtils: CurrencyUtils,
    private val orderToPromotionPresentationMapperMap: Map<String, OrderEntityToPromotionPresentationMapper>
) {
    fun map(orderEntity: OrderEntity): List<OrderPresentation> {
        if (orderEntity.items.isEmpty()) {
            return emptyList()
        }
        return mutableListOf<OrderPresentation>().apply {
            addAll(map(orderEntity.items))
            add(
                OrderPricePresentation(
                    orderId = orderEntity.orderId,
                    totalPrice = currencyUtils.getPriceWithCurrency(orderEntity.totalFinalPrice),
                    baseTotalPrice = currencyUtils.getPriceWithCurrency(orderEntity.totalBasePrice),
                    promotionDiscountedPrice = currencyUtils.getPriceWithCurrency(orderEntity.totalFinalPrice - orderEntity.totalBasePrice)
                )
            )
        }
    }

    private fun map(orderItems: List<OrderEntity.Item>): List<OrderItemPresentation> =
        orderItems.map { item ->
            OrderItemPresentation(
                productId = item.productId,
                productName = item.productName,
                quantity = "x" + item.quantity.toString(),
                itemPrice = currencyUtils.getPriceWithCurrency(item.unitBasePrice),
                subtotalPrice = currencyUtils.getPriceWithCurrency(item.unitFinalPrice * item.quantity),
                promotionPresentation = getPromotionPresentationMapper(item)
            )
        }

    private fun getPromotionPresentationMapper(
        orderItem: OrderEntity.Item
    ): PromotionPresentation? =
        orderItem.promotion?.let { promotion ->
            orderToPromotionPresentationMapperMap[promotion.appInternalId]?.map(orderItem)
        }
}