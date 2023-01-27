package com.cabify.mobilechallenge.cart.domain.usecase

import com.cabify.mobilechallenge.cart.domain.entity.CartEntity
import io.reactivex.rxjava3.core.Completable

interface AddProductToCartUseCase {
    operator fun invoke(item: CartEntity.Item): Completable
}