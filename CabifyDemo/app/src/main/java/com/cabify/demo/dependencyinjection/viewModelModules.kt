package com.cabify.demo.dependencyinjection

import com.cabify.demo.ui.HomeViewModel
import com.cabify.demo.ui.ShoppingCartViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {
    viewModel { HomeViewModel(get()) }
    viewModel { ShoppingCartViewModel() }
}