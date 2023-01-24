package com.cabify.shared.product.data.datasource

import com.cabify.shared.product.domain.entities.PromotionEntity
import io.reactivex.rxjava3.core.Single

internal interface PromotionsNetworkDataSource {
    fun getPromotions(): Single<List<PromotionEntity>>
}