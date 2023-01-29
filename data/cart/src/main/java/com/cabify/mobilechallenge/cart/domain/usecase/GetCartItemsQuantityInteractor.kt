package com.cabify.mobilechallenge.cart.domain.usecase

import com.cabify.mobilechallenge.cart.domain.entity.CartEntity
import com.cabify.mobilechallenge.cart.domain.repository.CartRepository
import io.reactivex.rxjava3.core.Observable

class GetCartItemsQuantityInteractor(private val cartRepository: CartRepository) :
    GetCartItemsQuantityUseCase {
    override fun invoke(): Observable<Int> =
        cartRepository.changes().map {
            it.items.sumOf(CartEntity.Item::quantity)
        }
}