package com.cabify.mobilechallenge.features.cart.domain.usecase

import com.cabify.mobilechallenge.features.cart.domain.entity.OrderEntity
import io.reactivex.rxjava3.core.Observable

interface GetOrderChangesUseCase {
    operator fun invoke(): Observable<OrderEntity>
}