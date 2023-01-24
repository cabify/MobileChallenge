package com.cabify.shared.product.domain.usecase

import com.cabify.shared.product.domain.entities.PromotionEntity
import io.reactivex.rxjava3.core.Single

interface GetPromotionsUseCase {
    operator fun invoke(): Single<List<PromotionEntity>>
}