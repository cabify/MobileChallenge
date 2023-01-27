package com.cabify.challenge.infrastructure.factory

import com.cabify.challenge.core.domain.products.Code
import com.cabify.challenge.core.domain.products.Price
import com.cabify.challenge.core.domain.products.Product
import com.cabify.challenge.core.domain.products.Products
import com.cabify.challenge.core.infrastructure.repositories.CartRepository
import com.cabify.challenge.core.infrastructure.repositories.ProductsRepository
import com.cabify.challenge.infrastructure.repositories.InMemoryCartRepository
import com.cabify.challenge.infrastructure.repositories.InMemoryItemsRepository

object RepositoriesFactory {
    val productsRepository: ProductsRepository by lazy { InMemoryItemsRepository(products = FakeProducts) }
    val cartRepository: CartRepository by lazy { InMemoryCartRepository() }

    private val mug = Product(
        code = Code.MUG,
        name = "Cabify Coffee Mug",
        price = Price.eurPrice(amount = 7.5)
    )

    private val tshirt = Product(
        code = Code.TSHIRT,
        name = "Cabify Coffee Mug",
        price = Price.eurPrice(amount = 20.0)
    )

    private val voucher = Product(
        code = Code.VOUCHER,
        name = "Cabify Voucher",
        price = Price.eurPrice(amount = 5.0)
    )
    private val FakeProducts = Products(
        products = listOf(
            mug, tshirt, voucher
        )
    )
}