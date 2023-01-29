package com.cabify.mobilechallenge.features.cart.presentation.mapper

import com.cabify.library.utils.CurrencyUtils
import com.cabify.library.utils.provider.StringsProvider
import com.cabify.mobilechallenge.core.base.mapper.Mapper
import com.cabify.mobilechallenge.features.cart.domain.entity.OrderEntity
import com.cabify.mobilechallenge.features.cart.presentation.model.OrderItemPresentation
import com.cabify.mobilechallenge.features.cart.presentation.model.OrderPresentation
import com.cabify.mobilechallenge.features.cart.presentation.model.OrderPricePresentation
import com.cabify.mobilechallenge.features.cart.presentation.model.PromotionPresentation
import com.cabify.mobilechallenge.shared.commonui.R

class OrderEntityToPresentationMapper(
    private val currencyUtils: CurrencyUtils,
    private val stringsProvider: StringsProvider,
    private val orderToPromotionPresentationMapperMap: Map<String, OrderEntityToPromotionPresentationMapper>
) : Mapper<OrderEntity, List<OrderPresentation>> {
    override fun map(input: OrderEntity): List<OrderPresentation> {
        if (input.items.isEmpty()) {
            return emptyList()
        }
        return mutableListOf<OrderPresentation>().apply {
            addAll(map(input.items))
            add(
                OrderPricePresentation(
                    orderId = input.orderId,
                    totalPrice = currencyUtils.getPriceWithCurrency(input.totalFinalPrice),
                    baseTotalPrice = currencyUtils.getPriceWithCurrency(input.totalBasePrice),
                    promotionDiscountedPrice = currencyUtils.getPriceWithCurrency(input.totalFinalPrice - input.totalBasePrice)
                )
            )
        }
    }

    private fun map(orderItems: List<OrderEntity.Item>): List<OrderItemPresentation> =
        orderItems.map { item ->
            val finalSubtotalPrice = item.unitFinalPrice * item.quantity
            val baseSubtotalPrice = item.unitBasePrice * item.quantity

            OrderItemPresentation(
                productId = item.productId,
                productName = item.productName,
                quantity = stringsProvider.getString(
                    R.string.quantity_x,
                    item.quantity
                ),
                unitBasePrice = stringsProvider.getString(
                    R.string.x_price_unit,
                    currencyUtils.getPriceWithCurrency(item.unitBasePrice)
                ),
                finalSubtotalPrice = currencyUtils.getPriceWithCurrency(finalSubtotalPrice),
                promotionPresentation = getPromotionPresentationMapper(item),
                unitFinalPrice = stringsProvider.getString(
                    R.string.x_price_unit,
                    currencyUtils.getPriceWithCurrency(item.unitFinalPrice)
                ),
                baseSubtotalPrice = currencyUtils.getPriceWithCurrency(baseSubtotalPrice),
                productImageUrl = item.productImageUrl
            )
        }

    private fun getPromotionPresentationMapper(
        orderItem: OrderEntity.Item
    ): PromotionPresentation? =
        orderItem.promotion?.let { promotion ->
            orderToPromotionPresentationMapperMap[promotion.appInternalId]?.map(orderItem)
        }
}