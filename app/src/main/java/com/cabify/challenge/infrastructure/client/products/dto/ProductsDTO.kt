package com.cabify.challenge.infrastructure.client.products.dto

import com.google.gson.annotations.SerializedName

data class ProductsDTO(@SerializedName("products") val products: List<ProductDTO>)
