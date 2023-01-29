package com.cabify.mobilechallenge.shared.cart.domain.usecase

import io.reactivex.rxjava3.core.Observable

interface GetCartItemsQuantityUseCase {
    operator fun invoke(): Observable<Int>
}