package com.cabify.shared.product.data.mapper

import com.cabify.library.utils.extensions.orZero
import com.cabify.mobilechallenge.core.base.mapper.Mapper
import com.cabify.shared.product.data.model.GetPromotionsResponse
import com.cabify.shared.product.domain.entities.BulkyItemsPromotionEntity
import com.cabify.shared.product.domain.entities.PromotionEntity

class BulkyPromotionResponseMapper : Mapper<GetPromotionsResponse.Promotion, PromotionEntity> {
    override fun map(input: GetPromotionsResponse.Promotion): PromotionEntity =
        BulkyItemsPromotionEntity(
            id = input.id.orEmpty(),
            name = input.name.orEmpty(),
            productTargetId = input.productTargetId.orEmpty(),
            minimumQuantity = input.minimumQuantity.orZero(),
            discountPercentagePerItem = input.discountPercentagePerItem.orZero()
        )

    companion object {
        const val BULKY_ITEM_DATA_ID = "BULKY_ITEMS"
    }
}