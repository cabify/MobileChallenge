package com.cabify.mobilechallenge.cart.domain.repository

import com.cabify.mobilechallenge.cart.domain.entity.CartEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface CartRepository {
    fun changes(): Observable<CartEntity>
    fun upsertQuantity(item: CartEntity.Item): Completable
    fun getItemFromCart(productId: String): Single<CartEntity.Item>
    fun deleteCart(): Completable
}