package com.cabify.mobilechallenge.features.cart.di

import com.cabify.mobilechallenge.features.cart.presentation.CartViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val cartModule = module {
    viewModel {
        CartViewModel()
    }
}