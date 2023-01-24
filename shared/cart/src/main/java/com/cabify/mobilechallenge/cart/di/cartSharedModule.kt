package com.cabify.mobilechallenge.cart.di

import com.cabify.mobilechallenge.cart.domain.entity.CartEntity
import com.cabify.mobilechallenge.cart.domain.repository.CartRepository
import com.cabify.mobilechallenge.cart.domain.usecase.AddProductToCartInteractor
import com.cabify.mobilechallenge.cart.domain.usecase.AddProductToCartUseCase
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.koin.dsl.module

val cartSharedModule = module {
    single<AddProductToCartUseCase> { AddProductToCartInteractor(get()) }
    single<CartRepository> {
        //TODO mocked
        object : CartRepository {
            override fun addProductToCart(productId: String, quantity: Int): Completable =
                Completable.complete()

            override fun getCart(): Single<CartEntity> =
                Single.just(
                    CartEntity(
                        listOf(
                            CartEntity.Item(productId = "VOUCHER", quantity = 1),
                            CartEntity.Item(productId = "TSHIRT", quantity = 4),
                            CartEntity.Item(productId = "MUG", quantity = 0)
                        )
                    )
                )
        }
    }
}