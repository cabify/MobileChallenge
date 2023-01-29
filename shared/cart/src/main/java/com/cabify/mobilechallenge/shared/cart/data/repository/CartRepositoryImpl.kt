package com.cabify.mobilechallenge.shared.cart.data.repository

import com.cabify.mobilechallenge.shared.cart.data.datasource.CartPersistenceDataSource
import com.cabify.mobilechallenge.shared.cart.domain.entity.CartEntity
import com.cabify.mobilechallenge.shared.cart.domain.repository.CartRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

internal class CartRepositoryImpl(private val cartPersistenceDataSource: CartPersistenceDataSource) :
    CartRepository {

    override fun upsertQuantity(item: CartEntity.Item): Completable =
        cartPersistenceDataSource.upsertQuantity(item)

    override fun changes(): Observable<CartEntity> =
        cartPersistenceDataSource.changes()

    override fun deleteCart(): Completable =
        cartPersistenceDataSource.deleteCart()

}