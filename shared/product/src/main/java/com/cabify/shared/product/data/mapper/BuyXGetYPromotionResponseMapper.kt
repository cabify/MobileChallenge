package com.cabify.shared.product.data.mapper

import com.cabify.library.utils.extensions.orZero
import com.cabify.mobilechallenge.core.base.mapper.Mapper
import com.cabify.shared.product.data.model.GetPromotionsResponse
import com.cabify.shared.product.domain.entities.BulkyItemsPromotionEntity
import com.cabify.shared.product.domain.entities.BuyXGetYFreePromotionEntity
import com.cabify.shared.product.domain.entities.PromotionEntity

class BuyXGetYPromotionResponseMapper : Mapper<GetPromotionsResponse.Promotion, PromotionEntity> {
    override fun map(input: GetPromotionsResponse.Promotion): PromotionEntity =
        BuyXGetYFreePromotionEntity(
            id = input.id.orEmpty(),
            name = input.name.orEmpty(),
            productTargetId = input.productTargetId.orEmpty(),
            minimumQuantity = input.minimumQuantity.orZero(),
            freeItemsQuantity = input.freeItemsQuantity.orZero(),
        )

    companion object {
        const val BUY_X_GET_Y_FREE_DATA_ID = "BUY_X_GET_Y_FREE"
    }
}