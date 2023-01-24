package com.cabify.mobilechallenge.cart.domain.usecase

import com.cabify.mobilechallenge.cart.domain.repository.CartRepository
import io.reactivex.rxjava3.core.Completable

internal class AddProductToCartInteractor(private val cartRepository: CartRepository) :
    AddProductToCartUseCase {
    override fun invoke(productId: String, quantity: Int): Completable =
        cartRepository.addProductToCart(productId, quantity)
}