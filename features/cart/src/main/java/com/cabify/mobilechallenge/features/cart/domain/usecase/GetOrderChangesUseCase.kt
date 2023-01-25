package com.cabify.mobilechallenge.features.cart.domain.usecase

import com.cabify.mobilechallenge.features.cart.domain.entity.Order
import io.reactivex.rxjava3.core.Observable

interface GetOrderChangesUseCase {
    operator fun invoke(): Observable<Order>
}