package com.cabify.shared.product.data.datasource

import com.cabify.mobilechallenge.core.base.mapper.Mapper
import com.cabify.shared.product.data.model.GetPromotionsResponse
import com.cabify.shared.product.data.service.PromotionsService
import com.cabify.shared.product.domain.entities.PromotionEntity
import io.reactivex.rxjava3.core.Single

internal class PromotionsNetworkDataSourceImpl(
    private val promotionsService: PromotionsService,
    private val getPromotionsResponseToDomainMapper: Mapper<GetPromotionsResponse, List<PromotionEntity>>
) :
    PromotionsNetworkDataSource {
    override fun getPromotions(): Single<List<PromotionEntity>> =
        promotionsService.getPromotions().map(getPromotionsResponseToDomainMapper::map)
}