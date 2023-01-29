package com.cabify.mobilechallenge.shared.cart.domain.usecase

import com.cabify.mobilechallenge.shared.cart.domain.entity.CartEntity
import com.cabify.mobilechallenge.shared.cart.domain.repository.CartRepository
import io.reactivex.rxjava3.core.Completable

internal class AddProductToCartInteractor(private val cartRepository: CartRepository) :
    AddProductToCartUseCase {
    override fun invoke(productId: String, quantity: Int): Completable =
        cartRepository.upsertQuantity(CartEntity.Item(productId = productId, quantity = quantity))
}
