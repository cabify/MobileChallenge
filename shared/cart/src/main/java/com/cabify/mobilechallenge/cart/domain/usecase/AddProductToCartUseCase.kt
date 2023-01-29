package com.cabify.mobilechallenge.cart.domain.usecase

import io.reactivex.rxjava3.core.Completable

interface AddProductToCartUseCase {
    operator fun invoke(productId: String, quantity: Int): Completable
}