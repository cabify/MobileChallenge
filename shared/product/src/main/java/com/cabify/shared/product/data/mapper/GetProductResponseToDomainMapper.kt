package com.cabify.shared.product.data.mapper

import com.cabify.library.utils.extensions.orZero
import com.cabify.mobilechallenge.core.base.mapper.Mapper
import com.cabify.shared.product.data.model.GetProductsResponse
import com.cabify.shared.product.domain.entities.ProductEntity

//Ideally these images should come in the API response but it is not the case
private val productIdProductImageUrl: Map<String, String> = hashMapOf(
    "MUG" to "https://goofy-shannon-8fec5b.netlify.com/mug.jpg",
    "TSHIRT" to "https://goofy-shannon-8fec5b.netlify.app/tshirt.jpg",
    "VOUCHER" to "https://goofy-shannon-8fec5b.netlify.app/voucher.jpg"
)

internal class GetProductResponseToDomainMapper : Mapper<GetProductsResponse, List<ProductEntity>> {
    override fun map(input: GetProductsResponse): List<ProductEntity> =
        input.products?.map {
            ProductEntity(
                id = it?.id.orEmpty(),
                name = it?.name.orEmpty(),
                price = it?.price.orZero(),
                productImageUrl = productIdProductImageUrl[it?.id]
            )
        }.orEmpty()
}