package com.cabify.challenge.infrastructure.client.products.api

import com.cabify.challenge.infrastructure.client.products.dto.ProductsDTO

interface APIClient {

    fun getProducts(): ProductsDTO
}