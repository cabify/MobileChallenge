package com.cabify.mobilechallenge.features.cart.domain.usecase

import com.cabify.mobilechallenge.cart.domain.entity.CartEntity
import com.cabify.mobilechallenge.cart.domain.repository.CartRepository
import com.cabify.mobilechallenge.features.cart.domain.entity.Order
import com.cabify.mobilechallenge.features.cart.domain.entity.OrderItem
import com.cabify.shared.product.domain.entities.ProductEntity
import com.cabify.shared.product.domain.entities.PromotionEntity
import com.cabify.shared.product.domain.repository.ProductsRepository
import com.cabify.shared.product.domain.repository.PromotionsRepository
import io.reactivex.rxjava3.core.Single

class GetOrderInteractor(
    private val cartRepository: CartRepository,
    private val productRepository: ProductsRepository,
    private val promotionsRepository: PromotionsRepository,
    private val promotionPriceStrategies: List<PromotionPriceStrategy>
) : GetOrderUseCase {

    private val productIdPromotionMap = HashMap<String, PromotionEntity>()
    private val productIdProductMap = HashMap<String, ProductEntity>()

    override fun invoke(): Single<Order> {
        return Single.zip(
            cartRepository.getCart(),
            productRepository.getProducts(),
            promotionsRepository.getPromotions()
        ) { cart, products, promotions ->
            promotions.forEach {
                productIdPromotionMap[it.productTargetId] = it
            }
            products.forEach {
                productIdProductMap[it.id] = it
            }

            val orderItems = mapToOrderItems(cart)

            Order(
                orderItems = mapToOrderItems(cart),
                finalTotalPrice = orderItems.sumOf { it.totalFinalPrice }
            )
        }
    }

    private fun mapToOrderItems(
        cart: CartEntity
    ): List<OrderItem> = cart.items.fold(emptyList()) { acc, cartItem ->
        productIdProductMap[cartItem.productId]?.let { product ->
            val promotion = productIdPromotionMap[product.id]
            val totalBasePrice = product.price * cartItem.quantity
            acc + OrderItem(
                productId = product.id,
                productName = product.name,
                quantity = cartItem.quantity,
                promotionName = promotion?.name,
                totalBasePrice = totalBasePrice,
                totalFinalPrice = getTotalFinalPrice(promotion, cartItem, product, totalBasePrice)
            )
        } ?: acc
    }

    private fun getTotalFinalPrice(
        promotion: PromotionEntity?,
        cartItem: CartEntity.Item,
        product: ProductEntity,
        totalBasePrice: Double
    ) = promotion?.let {
        promotionPriceStrategies.firstOrNull { it.isMatching(promotion) }?.apply(
            quantity = cartItem.quantity,
            productPrice = product.price,
            promotion = promotion
        )
    } ?: totalBasePrice
}