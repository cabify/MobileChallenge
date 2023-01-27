package com.cabify.mobilechallenge.persistence.dao

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface CartDAO<T : Any> {
    fun upsertQuantity(model: T): Completable
    fun insert(model: T): Completable
    fun update(model: T): Completable
    fun changes(): Observable<List<T>>
    fun read(id: String): Single<T>
}