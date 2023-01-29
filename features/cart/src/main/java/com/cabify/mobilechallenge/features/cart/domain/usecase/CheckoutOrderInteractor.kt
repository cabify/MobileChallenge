package com.cabify.mobilechallenge.features.cart.domain.usecase

import com.cabify.mobilechallenge.shared.cart.domain.repository.CartRepository
import io.reactivex.rxjava3.core.Completable

//We are removing the cart due to we don't have some kind of real payment
class CheckoutOrderInteractor(private val cartRepository: CartRepository): CheckoutOrderUseCase {
    override fun invoke(): Completable =
        cartRepository.deleteCart()
}