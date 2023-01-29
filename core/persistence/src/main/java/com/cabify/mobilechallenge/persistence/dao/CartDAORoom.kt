package com.cabify.mobilechallenge.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.rxjava3.EmptyResultSetException
import com.cabify.mobilechallenge.persistence.entity.CartItemData
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

@Dao
internal interface CartDAORoom : CartDAO<CartItemData> {
    override fun upsertQuantity(model: CartItemData): Completable =
        read(model.productId).flatMapCompletable {
            update(it.copy(quantity = it.quantity + model.quantity))
        }.onErrorResumeNext { throwable ->
            if (throwable is EmptyResultSetException) {
                insert(model)
            } else {
                Completable.error(throwable)
            }
        }

    @Update
    override fun update(model: CartItemData): Completable

    @Insert
    override fun insert(model: CartItemData): Completable

    @Query("SELECT * FROM cartItemData")
    override fun changes(): Observable<List<CartItemData>>

    @Query("SELECT * FROM cartItemData WHERE productId= :id LIMIT 1")
    override fun read(id: String): Single<CartItemData>

    @Query("DELETE FROM cartItemData")
    override fun delete(): Completable
}