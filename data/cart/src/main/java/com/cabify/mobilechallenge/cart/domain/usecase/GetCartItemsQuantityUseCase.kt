package com.cabify.mobilechallenge.cart.domain.usecase

import io.reactivex.rxjava3.core.Observable

interface GetCartItemsQuantityUseCase {
    operator fun invoke(): Observable<Int>
}