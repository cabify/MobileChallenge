package com.cabify.mobilechallenge.features.home.presentation.mapper

import com.cabify.library.utils.CurrencyUtils
import com.cabify.mobilechallenge.features.home.presentation.model.ProductPresentation
import com.cabify.mobilechallenge.features.home.presentation.model.ProductsPromotions

class ProductsPromotionsDomainToPresentationMapper(private val currencyUtils: CurrencyUtils) {
    fun map(productsPromotions: ProductsPromotions): List<ProductPresentation> {
        val (products, promotions) = productsPromotions
        return products.map { product ->
            ProductPresentation(
                id = product.id,
                name = product.name,
                price = currencyUtils.getPriceWithCurrencySymbol(product.price),
                availablePromotionName = promotions.firstOrNull { promotion -> promotion.productTargetId == product.id }?.name
            )
        }
    }
}