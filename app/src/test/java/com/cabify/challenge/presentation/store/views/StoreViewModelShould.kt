package com.cabify.challenge.presentation.store.views

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cabify.challenge.builder.ProductBuilder
import com.cabify.challenge.core.actions.AddProductToCart
import com.cabify.challenge.core.actions.GetProducts
import com.cabify.challenge.core.actions.GetProductsFromCart
import com.cabify.challenge.core.domain.products.Products
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class StoreViewModelShould {

    private lateinit var getProducts: GetProducts
    private lateinit var addProductToCart: AddProductToCart
    private lateinit var getProductsFromCart: GetProductsFromCart
    private lateinit var viewModel: StoreViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        getProducts = mockk(relaxed = true)
        addProductToCart = mockk(relaxed = true)
        getProductsFromCart = mockk(relaxed = true)
        viewModel = StoreViewModel(getProducts, addProductToCart, getProductsFromCart)

        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun `get products when start`() = runTest {
        givenProducts(products)

        whenStartViewModel()

        thenProductsAre(sameProducts)
    }

    @Test
    fun `add product to cart`() = runTest {

        viewModel.onAddToCart(voucher)

        verify { addProductToCart.invoke(voucher) }
    }

    @Test
    fun `get product from cart`() = runTest {
        coEvery { getProductsFromCart() } returns products

        viewModel.getProductsFromCart()

        assertEquals(products, viewModel.cart.value)
    }

    private fun thenProductsAre(products: Products) {
        assertEquals(products, viewModel.products.value)
    }

    private fun givenProducts(products: Products) {
        coEvery { getProducts() } returns products
    }

    private fun whenStartViewModel() {
        viewModel.start()
    }

    companion object {
        val products = Products(listOf(ProductBuilder().tshirt().build()))
        val sameProducts = Products(listOf(ProductBuilder().tshirt().build()))
        val voucher = ProductBuilder().voucher().build()
    }
}

