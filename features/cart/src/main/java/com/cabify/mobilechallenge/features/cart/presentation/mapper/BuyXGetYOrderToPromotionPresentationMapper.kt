package com.cabify.mobilechallenge.features.cart.presentation.mapper

import com.cabify.library.utils.provider.StringsProvider
import com.cabify.mobilechallenge.features.cart.domain.entity.OrderEntity
import com.cabify.mobilechallenge.features.cart.presentation.model.PromotionPresentation
import com.cabify.shared.product.domain.entities.BuyXGetYFreePromotionEntity

class BuyXGetYOrderToPromotionPresentationMapper(private val stringsProvider: StringsProvider) :
    OrderEntityToPromotionPresentationMapper {
    override fun map(orderItem: OrderEntity.Item): PromotionPresentation {
        val promotion = orderItem.promotion
        if (promotion!is BuyXGetYFreePromotionEntity) throw java.lang.IllegalArgumentException("BuyXGetYFreePromotionProcessor only supports BuyXGetYFreePromotionEntity")

        val timesMatchingPromotion = orderItem.quantity / promotion.minimumQuantity
        val freeItemsQuantity = timesMatchingPromotion * promotion.freeItemsQuantity
        return PromotionPresentation(
            promotionName = promotion.name,
            promotionInfo = stringsProvider.getString(
                com.cabify.mobilechallenge.shared.commonui.R.string.buy_x_get_y_promotion_info,
                freeItemsQuantity
            )
        )
    }
}