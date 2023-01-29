package com.cabify.mobilechallenge.features.cart.domain.usecase

import com.cabify.mobilechallenge.shared.cart.domain.repository.CartRepository
import com.cabify.mobilechallenge.features.cart.domain.entity.OrderEntity
import com.cabify.mobilechallenge.features.cart.domain.factory.OrderFactory
import com.cabify.shared.product.domain.repository.ProductsRepository
import com.cabify.shared.product.domain.repository.PromotionsRepository
import io.reactivex.rxjava3.core.Observable

class GetOrderChangesInteractor(
    private val cartRepository: CartRepository,
    private val productRepository: ProductsRepository,
    private val promotionsRepository: PromotionsRepository,
    private val orderFactory: OrderFactory
) : GetOrderChangesUseCase {

    override fun invoke(): Observable<OrderEntity> =
        Observable.combineLatest(
            productRepository.getProducts().toObservable(),
            promotionsRepository.getPromotions().toObservable(),
            cartRepository.changes()
        ) { products, promotions, cart ->
            orderFactory.create(cart, products, promotions)
        }
}