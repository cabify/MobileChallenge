package com.cabify.mobilechallenge.features.cart.di

import com.cabify.mobilechallenge.features.cart.domain.usecase.BulkyItemsPromotionPriceStrategy
import com.cabify.mobilechallenge.features.cart.domain.usecase.BuyXGetYFreePromotionPriceStrategy
import com.cabify.mobilechallenge.features.cart.domain.usecase.GetOrderInteractor
import com.cabify.mobilechallenge.features.cart.domain.usecase.GetOrderUseCase
import com.cabify.mobilechallenge.features.cart.presentation.CartViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val cartModule = module {
    viewModel {
        CartViewModel(get())
    }
    single<GetOrderUseCase> {
        GetOrderInteractor(get(), get(), get(), getAll())
    }
    single {
        BuyXGetYFreePromotionPriceStrategy()
    }
    single {
        BulkyItemsPromotionPriceStrategy()
    }
}