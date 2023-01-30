package com.cabify.challenge.infrastructure.client.products.api

import com.cabify.challenge.core.domain.products.Code
import com.cabify.challenge.core.domain.products.Price
import com.cabify.challenge.core.domain.products.Product
import com.cabify.challenge.core.domain.products.Products
import com.cabify.challenge.core.infrastructure.client.ProductsClient
import com.cabify.challenge.infrastructure.client.products.dto.ProductDTO

class RetrofitProductsClient(private val apiClient: APIClient) : ProductsClient {
    override suspend fun getProducts(): Products {
        return try {
            fetchProducts()

        } catch (e: Throwable) {
            Products(emptyList())
        }
    }

    private suspend fun fetchProducts(): Products {
        val call = apiClient.getProducts()
        val response = call.body()

        val productsResponse = response!!.products.map {
            it.toProduct()
        }
        return Products(productsResponse)
    }

    private fun ProductDTO.toProduct(): Product {
        val code = Code.valueOf(this.code)
        val price = Price.eurPrice(this.price)

        return Product(code, this.name, price)

    }
}