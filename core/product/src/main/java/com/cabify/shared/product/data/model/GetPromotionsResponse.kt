package com.cabify.shared.product.data.model


import com.google.gson.annotations.SerializedName

internal data class GetPromotionsResponse(
    @SerializedName("promotions")
    val promotions: List<Promotion>?
) {
    data class Promotion(
        @SerializedName("discountPercentagePerItem")
        val discountPercentagePerItem: Int?,
        @SerializedName("freeItemsQuantity")
        val freeItemsQuantity: Int?,
        @SerializedName("id")
        val id: String?,
        @SerializedName("minimumQuantity")
        val minimumQuantity: Int?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("productTargetId")
        val productTargetId: String?
    )
}