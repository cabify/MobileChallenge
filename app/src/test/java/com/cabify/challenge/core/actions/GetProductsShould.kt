package com.cabify.challenge.core.actions

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cabify.challenge.builder.ProductBuilder
import com.cabify.challenge.core.domain.products.Products
import com.cabify.challenge.core.infrastructure.client.ProductsClient
import com.cabify.challenge.core.infrastructure.repositories.ProductsRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetProductsShould {

    private lateinit var productsRepository: ProductsRepository
    private lateinit var productsClient: ProductsClient
    private lateinit var getProducts: GetProducts


    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        productsRepository = mockk(relaxed = true)
        productsClient = mockk()
    }


    @Test
    fun `return empty products`() = runTest {
        givenAGetProducts()
        givenA(emptyProducts)

        whenGetsProducts()

        thenTheProductsAre(Products(emptyList()))
    }


    @Test
    fun `return the products stored`() = runTest {
        givenAGetProducts()
        givenA(someProducts)

        whenGetsProducts()

        thenTheProductsAre(sameThatStored)
    }

    @Test
    fun `store products retrieved from client when there is not products stored`() = runTest {
        givenAGetProducts()
        givenA(noProducts)
        givenProductsFromClient()

        whenGetsProducts()

        thenSaveProducts()
    }

    private fun thenSaveProducts() {
        verify { productsRepository.save(someProducts) }
    }

    private fun givenProductsFromClient() {
        coEvery { productsClient.getProducts() } returns someProducts
    }

    private fun givenAGetProducts() {
        getProducts = GetProducts(productsRepository, productsClient)
    }

    private fun givenA(noProducts: Products?) {
        every { productsRepository.get() } returns noProducts
    }

    private suspend fun whenGetsProducts() {
        resultProducts = getProducts()
    }

    private fun thenTheProductsAre(products: Products) {
        assertEquals(resultProducts, products)
    }

    companion object {
        private lateinit var resultProducts: Products
        private val emptyProducts = Products(emptyList())
        private val noProducts: Products? = null
        private val someProducts = Products(listOf(ProductBuilder().mug().build()))
        private val sameThatStored = Products(listOf(ProductBuilder().mug().build()))
    }
}