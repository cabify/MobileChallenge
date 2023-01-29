package com.cabify.shared.product.data.mapper

import com.cabify.shared.product.data.model.GetPromotionsResponse
import com.cabify.shared.product.domain.entities.PromotionEntity
import com.cabify.mobilechallenge.core.base.mapper.Mapper

internal class GetPromotionsResponseToDomainMapper(private val promotionResponseMappers: Map<String, Mapper<GetPromotionsResponse.Promotion, PromotionEntity>>) :
    Mapper<GetPromotionsResponse, List<PromotionEntity>> {
    override fun map(input: GetPromotionsResponse): List<PromotionEntity> =
        input.promotions?.fold(emptyList<PromotionEntity>()) { acc, promotion ->
            promotionResponseMappers[promotion.id]?.map(promotion)?.let {
                acc + it
            } ?: acc
        }.orEmpty()
}