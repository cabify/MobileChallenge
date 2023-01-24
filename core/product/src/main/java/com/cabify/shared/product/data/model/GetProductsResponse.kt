package com.cabify.shared.product.data.model


import com.google.gson.annotations.SerializedName

internal data class GetProductsResponse(
    @SerializedName("products")
    val products: List<Product?>?
) {
    data class Product(
        @SerializedName("code")
        val code: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("price")
        val price: Double?
    )
}