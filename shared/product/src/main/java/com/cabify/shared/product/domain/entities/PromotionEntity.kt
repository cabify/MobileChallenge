package com.cabify.shared.product.domain.entities

sealed class PromotionEntity {
    abstract val id: String
    abstract val appInternalId :String
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
) : PromotionEntity(){
    override val appInternalId: String
        get() = APP_INTERNAL_ID
    companion object{
         const val APP_INTERNAL_ID = "BULKY_ITEM_ID"
    }
}

data class BuyXGetYFreePromotionEntity(
    override val id: String,
    override val name: String,
    override val productTargetId: String,
    override val minimumQuantity: Int,
    val freeItemsQuantity: Int
) : PromotionEntity(){
    override val appInternalId: String
        get() = INTERNAL_ID
    companion object{
         const val INTERNAL_ID = "BUY_X_GET_Y_FREE_ID"
    }
}