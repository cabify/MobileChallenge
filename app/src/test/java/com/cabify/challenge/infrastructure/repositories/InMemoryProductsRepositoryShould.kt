package com.cabify.challenge.infrastructure.repositories

import com.cabify.challenge.builder.ProductBuilder
import com.cabify.challenge.core.domain.products.Products
import com.cabify.challenge.core.infrastructure.repositories.ProductsRepository
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class InMemoryProductsRepositoryShould {

    private lateinit var repository: ProductsRepository

    @Before
    fun setUp() {
        repository = InMemoryProductsRepository()
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

        thenReturnProducts(someProducts)
    }

    private fun thenReturnProducts(products: Products?) {
        assertEquals(resultProducts, products)
    }

    private fun whenGetsProducts() {
        resultProducts = repository.get()
    }

    private fun givenSomeStoredProducts() {
        repository.save(someProducts)
    }

    companion object {
        private var resultProducts: Products? = null
        private var noProducts: Products? = null
        private val someProducts = Products(
            products = listOf(ProductBuilder().voucher().build())
        )
    }
}