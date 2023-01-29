package com.cabify.mobilechallenge.shared.cart.di

import CartPersistenceDataSourceImpl
import com.cabify.mobilechallenge.shared.cart.data.datasource.CartPersistenceDataSource
import com.cabify.mobilechallenge.shared.cart.data.mapper.CartItemDataMapper
import com.cabify.mobilechallenge.shared.cart.data.mapper.CartItemsDataMapper
import com.cabify.mobilechallenge.shared.cart.data.mapper.CartItemEntityMapper
import com.cabify.mobilechallenge.shared.cart.data.repository.CartRepositoryImpl
import com.cabify.mobilechallenge.shared.cart.domain.repository.CartRepository
import com.cabify.mobilechallenge.shared.cart.domain.usecase.AddProductToCartInteractor
import com.cabify.mobilechallenge.shared.cart.domain.usecase.AddProductToCartUseCase
import com.cabify.mobilechallenge.shared.cart.domain.usecase.GetCartItemsQuantityInteractor
import com.cabify.mobilechallenge.shared.cart.domain.usecase.GetCartItemsQuantityUseCase
import org.koin.dsl.module

val cartSharedModule = module {
    single<AddProductToCartUseCase> { AddProductToCartInteractor(get()) }
    single<GetCartItemsQuantityUseCase> { GetCartItemsQuantityInteractor(get()) }
    single<CartRepository> {
        CartRepositoryImpl(get())
    }
    single<CartPersistenceDataSource> {
        CartPersistenceDataSourceImpl(get(), get<CartItemsDataMapper>(), get<CartItemEntityMapper>(), )
    }
    single { CartItemsDataMapper(get<CartItemDataMapper>()) }
    single { CartItemDataMapper() }
    single { CartItemEntityMapper() }
}