package com.cabify.challenge.infrastructure.factory

import com.cabify.challenge.core.infrastructure.repositories.CartRepository
import com.cabify.challenge.core.infrastructure.repositories.ProductsRepository
import com.cabify.challenge.infrastructure.repositories.InMemoryCartRepository
import com.cabify.challenge.infrastructure.repositories.InMemoryItemsRepository

object RepositoriesFactory {
    val productsRepository: ProductsRepository by lazy { InMemoryItemsRepository() }
    val cartRepository: CartRepository by lazy { InMemoryCartRepository() }

}