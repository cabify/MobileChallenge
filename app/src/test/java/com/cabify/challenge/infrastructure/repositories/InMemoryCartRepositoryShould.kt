package com.cabify.challenge.infrastructure.repositories

import com.cabify.challenge.builder.ProductBuilder
import com.cabify.challenge.core.domain.products.Product
import com.cabify.challenge.core.domain.products.Products
import com.cabify.challenge.core.infrastructure.repositories.CartRepository
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class InMemoryCartRepositoryShould {

    private lateinit var repository: CartRepository

    @Before
    fun setUp() {
        repository = InMemoryCartRepository()
    }

    @Test
    fun `retrieve no products`() {

        whenGetsProducts()

        thenReturnProducts(noProducts)
    }

    @Test
    fun `retrieve stored products`() {
        givenSomeStoredProducts()

        whenGetsProducts()

        thenReturnProducts(cart)
    }

    private fun thenReturnProducts(products: Products) {
        assertEquals(resultProducts, products)
    }

    private fun whenGetsProducts() {
        resultProducts = repository.get()
    }

    private fun givenSomeStoredProducts() {
        repository.save(aVoucher)
    }

    companion object {
        private var resultProducts: Products = Products(emptyList())
        private var noProducts: Products = Products(emptyList())
        private var cart: Products = Products(listOf(ProductBuilder().voucher().build()))
        private val aVoucher: Product = ProductBuilder().voucher().build()
    }
}

