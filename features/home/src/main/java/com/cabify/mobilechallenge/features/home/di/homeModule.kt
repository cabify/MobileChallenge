package com.cabify.mobilechallenge.features.home.di

import com.cabify.mobilechallenge.features.home.presentation.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    viewModel {
        HomeViewModel()
    }
}