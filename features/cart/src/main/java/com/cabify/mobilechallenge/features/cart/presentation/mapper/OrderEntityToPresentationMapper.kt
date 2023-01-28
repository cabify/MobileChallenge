package com.cabify.mobilechallenge.features.cart.presentation.mapper

import com.cabify.library.utils.CurrencyUtils
import com.cabify.mobilechallenge.features.cart.domain.entity.OrderEntity
import com.cabify.mobilechallenge.features.cart.presentation.model.OrderItemPresentation
import com.cabify.mobilechallenge.features.cart.presentation.model.OrderPresentation
import com.cabify.mobilechallenge.features.cart.presentation.model.OrderPricePresentation

class OrderEntityToPresentationMapper(private val currencyUtils: CurrencyUtils) {
    fun map(orderEntity: OrderEntity): List<OrderPresentation> =
        mutableListOf<OrderPresentation>().apply {
            addAll(map(orderEntity.items))
            add(
                OrderPricePresentation(
                    orderId = orderEntity.orderId,
                    totalPrice = currencyUtils.getPriceWithCurrencySymbol(orderEntity.totalFinalPrice),
                    basePrice = currencyUtils.getPriceWithCurrencySymbol(orderEntity.totalBasePrice)
                )
            )
        }

    private fun map(orderItems: List<OrderEntity.Item>): List<OrderItemPresentation> =
        orderItems.groupBy { it.productId }.map { productIdOrderItemMap ->
            val productItem = productIdOrderItemMap.value.first()
            val productId = productIdOrderItemMap.key
            val quantity = productIdOrderItemMap.value.size
            val subtotalPrice =
                currencyUtils.getPriceWithCurrencySymbol(price = productIdOrderItemMap.value.sumOf { it.unitFinalPrice })
            OrderItemPresentation(
                productId = productId,
                productName = productItem.productName,
                quantity = quantity.toString(),
                itemPrice = currencyUtils.getPriceWithCurrencySymbol(price = productItem.unitBasePrice),
                subtotalPrice = subtotalPrice,
                promotionName = null,
                promotionInfo = null
            )
        }
}