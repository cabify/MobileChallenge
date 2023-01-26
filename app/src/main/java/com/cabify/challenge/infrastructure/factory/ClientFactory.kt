package com.cabify.challenge.infrastructure.factory

import com.cabify.challenge.core.domain.products.Products
import com.cabify.challenge.core.infrastructure.client.ProductsClient

object ClientFactory {
    val retrofitClient = RetrofitClient()

    class RetrofitClient : ProductsClient {
        override fun getProducts(): Products {
            return Products(emptyList())
        }
    }
}
