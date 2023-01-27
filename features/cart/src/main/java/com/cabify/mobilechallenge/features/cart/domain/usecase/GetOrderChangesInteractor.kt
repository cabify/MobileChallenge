package com.cabify.mobilechallenge.features.cart.domain.usecase

import com.cabify.mobilechallenge.cart.domain.repository.CartRepository
import com.cabify.mobilechallenge.features.cart.domain.entity.OrderEntity
import com.cabify.mobilechallenge.features.cart.domain.factory.OrderFactory
import com.cabify.shared.product.domain.repository.ProductsRepository
import com.cabify.shared.product.domain.repository.PromotionsRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class GetOrderChangesInteractor(
    private val cartRepository: CartRepository,
    private val productRepository: ProductsRepository,
    private val promotionsRepository: PromotionsRepository,
    private val orderFactory: OrderFactory
) : GetOrderChangesUseCase {

    override fun invoke(): Observable<OrderEntity> =
        cartRepository.changes()
            .switchMapSingle { cart ->
                Single.zip(
                    productRepository.getProducts(),
                    promotionsRepository.getPromotions()
                ) { products, promotions ->
                    orderFactory.create(cart, products, promotions)
                }
            }
}