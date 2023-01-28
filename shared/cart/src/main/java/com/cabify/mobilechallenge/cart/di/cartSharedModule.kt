package com.cabify.mobilechallenge.cart.di

import CartPersistenceDataSourceImpl
import com.cabify.mobilechallenge.cart.data.datasource.CartPersistenceDataSource
import com.cabify.mobilechallenge.cart.data.mapper.CartItemDataMapper
import com.cabify.mobilechallenge.cart.data.mapper.CartItemEntityMapper
import com.cabify.mobilechallenge.cart.data.repository.CartRepositoryImpl
import com.cabify.mobilechallenge.cart.domain.repository.CartRepository
import com.cabify.mobilechallenge.cart.domain.usecase.AddProductToCartInteractor
import com.cabify.mobilechallenge.cart.domain.usecase.AddProductToCartUseCase
import com.cabify.mobilechallenge.cart.domain.usecase.GetCartItemsQuantityInteractor
import com.cabify.mobilechallenge.cart.domain.usecase.GetCartItemsQuantityUseCase
import org.koin.dsl.module

val cartSharedModule = module {
    single<AddProductToCartUseCase> { AddProductToCartInteractor(get()) }
    single<GetCartItemsQuantityUseCase> { GetCartItemsQuantityInteractor(get()) }
    single<CartRepository> {
        CartRepositoryImpl(get())
    }
    single<CartPersistenceDataSource> {
        CartPersistenceDataSourceImpl(get(), get(), get())
    }
    single { CartItemDataMapper() }
    single { CartItemEntityMapper() }
}