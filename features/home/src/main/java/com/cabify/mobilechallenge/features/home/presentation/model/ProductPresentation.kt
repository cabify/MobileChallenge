package com.cabify.mobilechallenge.features.home.presentation.model

import com.cabify.shared.product.domain.entities.ProductEntity
import com.cabify.shared.product.domain.entities.PromotionEntity

typealias ProductsPromotions = Pair<List<ProductEntity>, List<PromotionEntity>>

data class ProductPresentation(
    val id: String,
    val name: String,
    val price: String,
    val productImageUrl: String?,
    val availablePromotionName: String?
)