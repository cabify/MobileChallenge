package com.cabify.shared.product.data.mapper

import com.cabify.library.utils.extensions.orZero
import com.cabify.shared.product.data.model.GetPromotionsResponse
import com.cabify.shared.product.domain.entities.BulkyItemsPromotionEntity
import com.cabify.shared.product.domain.entities.BuyXGetYFreePromotionEntity
import com.cabify.shared.product.domain.entities.PromotionEntity

internal class GetPromotionsResponseToDomainMapper {
    fun map(getPromotionsResponse: GetPromotionsResponse): List<PromotionEntity> =
        getPromotionsResponse.promotions?.fold(emptyList<PromotionEntity>()) { acc, promotion ->
            when (promotion.id) {
                BULKY_ITEM_ID -> {
                    acc + BulkyItemsPromotionEntity(
                        id = promotion.id,
                        name = promotion.name.orEmpty(),
                        productTargetId = promotion.name.orEmpty(),
                        minimumQuantity = promotion.minimumQuantity.orZero(),
                        discountPercentagePerItem = promotion.discountPercentagePerItem.orZero()
                    )
                }
                BUY_X_GET_Y_FREE_ID -> {
                    acc + BuyXGetYFreePromotionEntity(
                        id = promotion.id,
                        name = promotion.name.orEmpty(),
                        productTargetId = promotion.name.orEmpty(),
                        minimumQuantity = promotion.minimumQuantity.orZero(),
                        freeItemsQuantity = promotion.freeItemsQuantity.orZero(),
                    )
                }
                else -> acc
            }
        }.orEmpty()

    companion object {
        private const val BULKY_ITEM_ID = "BULKY_ITEMS"
        private const val BUY_X_GET_Y_FREE_ID = "BUY_X_GET_Y_FREE"
    }
}