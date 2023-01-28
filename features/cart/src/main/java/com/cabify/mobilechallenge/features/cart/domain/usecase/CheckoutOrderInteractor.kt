package com.cabify.mobilechallenge.features.cart.domain.usecase

import com.cabify.mobilechallenge.cart.domain.repository.CartRepository
import io.reactivex.rxjava3.core.Completable

class CheckoutOrderInteractor(private val cartRepository: CartRepository): CheckoutOrderUseCase {
    override fun invoke(): Completable =
        cartRepository.deleteCart()
}