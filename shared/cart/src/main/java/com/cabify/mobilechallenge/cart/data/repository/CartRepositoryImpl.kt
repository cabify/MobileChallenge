package com.cabify.mobilechallenge.cart.data.repository

import com.cabify.mobilechallenge.cart.data.datasource.CartPersistenceDataSource
import com.cabify.mobilechallenge.cart.domain.entity.CartEntity
import com.cabify.mobilechallenge.cart.domain.repository.CartRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class CartRepositoryImpl(private val cartPersistenceDataSource: CartPersistenceDataSource) :
    CartRepository {

    override fun upsertQuantity(item: CartEntity.Item): Completable =
        cartPersistenceDataSource.upsertQuantity(item)

    override fun changes(): Observable<CartEntity> =
        cartPersistenceDataSource.changes()

    override fun getItemFromCart(productId: String): Single<CartEntity.Item> =
        cartPersistenceDataSource.getItemFromCart(productId)

    override fun deleteCart(): Completable =
        cartPersistenceDataSource.deleteCart()

}