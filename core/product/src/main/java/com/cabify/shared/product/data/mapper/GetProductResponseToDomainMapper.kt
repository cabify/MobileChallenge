package com.cabify.shared.product.data.mapper

import com.cabify.library.utils.extensions.orZero
import com.cabify.shared.product.data.model.GetProductsResponse
import com.cabify.shared.product.domain.entities.ProductEntity

internal class GetProductResponseToDomainMapper {
    fun map(getProductsResponse: GetProductsResponse): List<ProductEntity> =
        getProductsResponse.products?.map {
            ProductEntity(
                id = it?.id.orEmpty(),
                name = it?.id.orEmpty(),
                price = it?.price.orZero()
            )
        }.orEmpty()
}