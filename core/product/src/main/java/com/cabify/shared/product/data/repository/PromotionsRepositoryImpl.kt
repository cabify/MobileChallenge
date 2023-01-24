package com.cabify.shared.product.data.repository

import com.cabify.shared.product.data.datasource.PromotionsNetworkDataSource
import com.cabify.shared.product.domain.entities.PromotionEntity
import com.cabify.shared.product.domain.repository.PromotionsRepository
import io.reactivex.rxjava3.core.Single

internal class PromotionsRepositoryImpl(private val promotionsNetworkDataSource: PromotionsNetworkDataSource) :
    PromotionsRepository {
    override fun getPromotions(): Single<List<PromotionEntity>> =
        promotionsNetworkDataSource.getPromotions()
}