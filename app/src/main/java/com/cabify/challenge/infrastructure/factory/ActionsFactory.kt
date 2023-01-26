package com.cabify.challenge.infrastructure.factory

import com.cabify.challenge.core.actions.GetProducts

object ActionsFactory {

    fun createGetProductsActions(): GetProducts {
        return GetProducts(
            RepositoriesFactory.productsRepository,
            ClientFactory.retrofitClient
        )
    }
}