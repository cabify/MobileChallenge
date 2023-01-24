package com.cabify.shared.product.data.datasource

import com.cabify.shared.product.data.mapper.GetPromotionsResponseToDomainMapper
import com.cabify.shared.product.data.service.PromotionsService
import com.cabify.shared.product.domain.entities.PromotionEntity
import io.reactivex.rxjava3.core.Single

internal class PromotionsNetworkDataSourceImpl(
    private val promotionsService: PromotionsService,
    private val getPromotionsResponseToDomainMapper: GetPromotionsResponseToDomainMapper
) :
    PromotionsNetworkDataSource {
    override fun getPromotions(): Single<List<PromotionEntity>> =
        promotionsService.getPromotions().map(getPromotionsResponseToDomainMapper::map)
}