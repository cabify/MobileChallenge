package com.cabify.mobilechallenge.features.home.presentation.mapper

import com.cabify.library.utils.CurrencyUtils
import com.cabify.mobilechallenge.features.home.presentation.model.ProductPresentation
import com.cabify.mobilechallenge.features.home.presentation.model.ProductsPromotions
import com.cabify.shared.product.domain.entities.PromotionEntity

class ProductsPromotionsDomainToPresentationMapper(private val currencyUtils: CurrencyUtils) {
    private val productIdPromotionsMap = HashMap<String, PromotionEntity>()

    fun map(productsPromotions: ProductsPromotions): List<ProductPresentation> {
        val (products, promotions) = productsPromotions

        promotions.forEach { promotion ->
            productIdPromotionsMap[promotion.productTargetId] = promotion
        }

        return products.map { product ->
            ProductPresentation(
                id = product.id,
                name = product.name,
                price = currencyUtils.getPriceWithCurrency(product.price),
                availablePromotionName = productIdPromotionsMap[product.id]?.name
            )
        }
    }
}