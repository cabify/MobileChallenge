package com.cabify.mobilechallenge.cart.data.datasource

import com.cabify.mobilechallenge.cart.domain.entity.CartEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface CartPersistenceDataSource {
    fun changes(): Observable<CartEntity>
    fun upsertQuantity(item: CartEntity.Item): Completable
    fun deleteCart(): Completable
}