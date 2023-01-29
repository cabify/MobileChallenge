package com.cabify.mobilechallenge.shared.cart.domain.repository

import com.cabify.mobilechallenge.shared.cart.domain.entity.CartEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

interface CartRepository {
    fun changes(): Observable<CartEntity>
    fun upsertQuantity(item: CartEntity.Item): Completable
    fun deleteCart(): Completable
}