package com.cabify.mobilechallenge.features.cart.presentation.mapper

import com.cabify.library.utils.provider.StringsProvider
import com.cabify.mobilechallenge.features.cart.domain.entity.OrderEntity

import com.cabify.mobilechallenge.features.cart.presentation.model.PromotionPresentation
import com.cabify.shared.product.domain.entities.BulkyItemsPromotionEntity

class BulkyItemsOrderToPromotionPresentationMapper(private val stringsProvider: StringsProvider) :
    OrderEntityToPromotionPresentationMapper {
    override fun map(input: OrderEntity.Item): PromotionPresentation {
        val promotion = input.promotion
        if (promotion !is BulkyItemsPromotionEntity) throw java.lang.IllegalArgumentException("BulkyItemsPromotionToPresentationMapper only supports BulkyItemsPromotionEntity")

        return PromotionPresentation(
            promotionName = input.promotion.name,
            promotionInfo = stringsProvider.getString(
                com.cabify.mobilechallenge.shared.commonui.R.string.bulky_items_promotion_info,
                promotion.discountPercentagePerItem
            )
        )
    }
}