package com.cabify.challenge.presentation.store.views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cabify.challenge.infrastructure.factory.ActionsFactory

class StoreViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val getProducts = ActionsFactory.createGetProductsActions()
        val addProductToCart = ActionsFactory.createAddProductToCartActions()
        val getProductsFromCart = ActionsFactory.createGetProductToCartActions()

        if (modelClass.isAssignableFrom(StoreViewModel::class.java)) {
            return StoreViewModel(
                getProducts,
                addProductToCart,
                getProductsFromCart
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}