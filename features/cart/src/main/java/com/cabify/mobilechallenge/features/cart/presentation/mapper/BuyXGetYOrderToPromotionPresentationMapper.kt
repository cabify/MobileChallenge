package com.cabify.mobilechallenge.features.cart.presentation.mapper

import com.cabify.library.utils.provider.StringsProvider
import com.cabify.mobilechallenge.features.cart.domain.entity.OrderEntity
import com.cabify.mobilechallenge.features.cart.presentation.model.PromotionPresentation
import com.cabify.shared.product.domain.entities.BuyXGetYFreePromotionEntity

class BuyXGetYOrderToPromotionPresentationMapper(private val stringsProvider: StringsProvider) :
    OrderEntityToPromotionPresentationMapper {
    override fun map(input: OrderEntity.Item): PromotionPresentation {
        val promotion = input.promotion
        if (promotion !is BuyXGetYFreePromotionEntity) throw java.lang.IllegalArgumentException("BuyXGetYFreePromotionProcessor only supports BuyXGetYFreePromotionEntity")

        val timesMatchingPromotion = input.quantity / promotion.minimumQuantity
        val freeItemsQuantity = timesMatchingPromotion * promotion.freeItemsQuantity
        return PromotionPresentation(
            promotionName = promotion.name,
            promotionInfo = stringsProvider.getPlural(
                com.cabify.mobilechallenge.shared.commonui.R.plurals.buy_x_get_y_promotion_info,
                freeItemsQuantity,
                freeItemsQuantity
            )
        )
    }
}