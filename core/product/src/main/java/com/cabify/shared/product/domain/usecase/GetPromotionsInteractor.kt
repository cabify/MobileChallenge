package com.cabify.shared.product.domain.usecase

import com.cabify.shared.product.domain.entities.PromotionEntity
import com.cabify.shared.product.domain.repository.PromotionsRepository
import io.reactivex.rxjava3.core.Single

internal class GetPromotionsInteractor(private val promotionsRepository: PromotionsRepository) :
    GetPromotionsUseCase {
    override fun invoke(): Single<List<PromotionEntity>> =
        promotionsRepository.getPromotions()
}