package com.cabify.challenge.infrastructure.factory

import com.cabify.challenge.core.actions.AddProductToCart
import com.cabify.challenge.core.actions.ConfirmPurchase
import com.cabify.challenge.core.actions.GetProducts
import com.cabify.challenge.core.actions.GetProductsFromCart

object ActionsFactory {

    fun createGetProductsActions(): GetProducts {
        return GetProducts(
            RepositoriesFactory.productsRepository,
            ClientFactory.retrofitClient
        )
    }

    fun createAddProductToCartActions(): AddProductToCart {
        return AddProductToCart(
            RepositoriesFactory.cartRepository
        )
    }

    fun createGetProductToCartActions(): GetProductsFromCart {
        return GetProductsFromCart(
            RepositoriesFactory.cartRepository,
            ServiceFactory.promosService
        )
    }

    fun createConfirmPurchaseActions(): ConfirmPurchase {
        return ConfirmPurchase(RepositoriesFactory.cartRepository)
    }
}