package com.cabify.challenge.infrastructure.factory

import com.cabify.challenge.core.domain.products.Code
import com.cabify.challenge.core.domain.products.Price
import com.cabify.challenge.core.domain.products.Product
import com.cabify.challenge.core.domain.products.Products
import com.cabify.challenge.infrastructure.repositories.InMemoryProductsRepository

object RepositoriesFactory {
    val productsRepository by lazy { InMemoryProductsRepository(products = FakeProducts) }

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