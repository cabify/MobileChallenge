package com.cabify.shared.product.domain.repository

import com.cabify.shared.product.domain.entities.PromotionEntity
import io.reactivex.rxjava3.core.Single

interface PromotionsRepository {
    fun getPromotions(): Single<List<PromotionEntity>>
}