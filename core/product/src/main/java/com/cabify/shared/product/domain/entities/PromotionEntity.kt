package com.cabify.shared.product.domain.entities

sealed class PromotionEntity {
    abstract val id: String
    abstract val name: String
    abstract val productTargetId: String
    abstract val minimumQuantity: Int
}

data class BulkyItemsPromotionEntity(
    override val id: String,
    override val name: String,
    override val productTargetId: String,
    override val minimumQuantity: Int,
    val discountPercentagePerItem: Int
) : PromotionEntity()

data class BuyXGetYFreePromotionEntity(
    override val id: String,
    override val name: String,
    override val productTargetId: String,
    override val minimumQuantity: Int,
    val freeItemsQuantity: Int
) : PromotionEntity()