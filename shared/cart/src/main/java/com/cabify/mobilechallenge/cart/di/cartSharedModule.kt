package com.cabify.mobilechallenge.cart.di

import CartPersistenceDataSourceImpl
import com.cabify.mobilechallenge.cart.data.datasource.CartPersistenceDataSource
import com.cabify.mobilechallenge.cart.data.mapper.CartItemDataMapper
import com.cabify.mobilechallenge.cart.data.mapper.CartItemsDataMapper
import com.cabify.mobilechallenge.cart.data.mapper.CartItemEntityMapper
import com.cabify.mobilechallenge.cart.data.repository.CartRepositoryImpl
import com.cabify.mobilechallenge.cart.domain.entity.CartEntity
import com.cabify.mobilechallenge.cart.domain.repository.CartRepository
import com.cabify.mobilechallenge.cart.domain.usecase.AddProductToCartInteractor
import com.cabify.mobilechallenge.cart.domain.usecase.AddProductToCartUseCase
import com.cabify.mobilechallenge.cart.domain.usecase.GetCartItemsQuantityInteractor
import com.cabify.mobilechallenge.cart.domain.usecase.GetCartItemsQuantityUseCase
import com.cabify.mobilechallenge.core.base.mapper.Mapper
import com.cabify.mobilechallenge.persistence.entity.CartItemData
import org.koin.dsl.module

val cartSharedModule = module {
    single<AddProductToCartUseCase> { AddProductToCartInteractor(get()) }
    single<GetCartItemsQuantityUseCase> { GetCartItemsQuantityInteractor(get()) }
    single<CartRepository> {
        CartRepositoryImpl(get())
    }
    single<CartPersistenceDataSource> {
        CartPersistenceDataSourceImpl(get(), get(), get(), get())
    }
    single { CartItemsDataMapper(get()) }
    single { CartItemDataMapper() }
    single { CartItemEntityMapper() }
}