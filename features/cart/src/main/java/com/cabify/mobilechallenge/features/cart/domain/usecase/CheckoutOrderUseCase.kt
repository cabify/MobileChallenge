package com.cabify.mobilechallenge.features.cart.domain.usecase

import io.reactivex.rxjava3.core.Completable

interface CheckoutOrderUseCase {
    operator fun invoke(): Completable
}