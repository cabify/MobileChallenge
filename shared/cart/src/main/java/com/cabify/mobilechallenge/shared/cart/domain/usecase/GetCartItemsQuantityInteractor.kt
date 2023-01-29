package com.cabify.mobilechallenge.shared.cart.domain.usecase

import com.cabify.mobilechallenge.shared.cart.domain.entity.CartEntity
import com.cabify.mobilechallenge.shared.cart.domain.repository.CartRepository
import io.reactivex.rxjava3.core.Observable

internal class GetCartItemsQuantityInteractor(private val cartRepository: CartRepository) :
    GetCartItemsQuantityUseCase {
    override fun invoke(): Observable<Int> =
        cartRepository.changes().map {
            it.items.sumOf(CartEntity.Item::quantity)
        }
}