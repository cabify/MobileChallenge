package com.cabify.mobilechallenge.cart.domain.repository

import com.cabify.mobilechallenge.cart.domain.entity.CartEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface CartRepository {
    fun addProductToCart(productId: String, quantity: Int): Completable
    fun getCart(): Single<CartEntity>
}