package com.cabify.challenge.infrastructure.client.products.dto

import com.google.gson.annotations.SerializedName

data class ProductDTO(
    @SerializedName("code")  val code: String,
    @SerializedName("name")  val name: String,
    @SerializedName("price")  val price: Double
)