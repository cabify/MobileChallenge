package com.cabify.shared.product.data.service

import com.cabify.shared.product.data.model.GetProductsResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

internal interface ProductsService {
    @GET(GET_PRODUCTS_PATH)
    fun getProducts(): Single<GetProductsResponse>

    private companion object {
        private const val GET_PRODUCTS_PATH =
            "/palcalde/6c19259bd32dd6aafa327fa557859c2f/raw/ba51779474a150ee4367cda4f4ffacdcca479887/Products.json"
    }
}