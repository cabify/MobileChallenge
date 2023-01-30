package com.cabify.challenge.presentation.store.views

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cabify.challenge.builder.ProductBuilder
import com.cabify.challenge.core.actions.AddProductToCart
import com.cabify.challenge.core.actions.ConfirmPurchase
import com.cabify.challenge.core.actions.GetProducts
import com.cabify.challenge.core.actions.GetProductsFromCart
import com.cabify.challenge.core.domain.cart.Cart
import com.cabify.challenge.core.domain.products.Products
import com.cabify.challenge.presentation.views.StoreViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
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
    private lateinit var confirmPurchase: ConfirmPurchase
    private lateinit var viewModel: StoreViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        getProducts = mockk(relaxed = true)
        addProductToCart = mockk(relaxed = true)
        getProductsFromCart = mockk(relaxed = true)
        confirmPurchase = mockk(relaxed = true)
        viewModel = StoreViewModel(
            getProducts,
            addProductToCart,
            getProductsFromCart,
            confirmPurchase
        )

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

        whenAddToCart()

        thenProductsWereAddedToCart()
    }


    @Test
    fun `get product from cart`() = runTest {
        givenACart()

        whenGetProductsFromCart()

        thenProductsWereRetrieved()
    }

    @Test
    fun `confirm purchase`() = runTest {
        givenACart()

        whenConfirmPurchase()

        thenConfirmThePurchase()
    }


    private fun givenACart() {
        coEvery { getProductsFromCart() } returns Cart(products, emptyList())
    }

    private fun givenProducts(products: Products) {
        coEvery { getProducts() } returns products
    }

    private fun whenAddToCart() {
        viewModel.onAddToCart(voucher)
    }

    private fun whenGetProductsFromCart() {
        viewModel.onGetProductFromCart()
    }

    private fun whenConfirmPurchase() {
        viewModel.onConfirmPurchase()
    }

    private fun whenStartViewModel() {
        viewModel.start()
    }

    private fun thenProductsAre(products: Products) {
        assertEquals(products, viewModel.products.value)
    }

    private fun thenProductsWereAddedToCart() {
        coVerify { addProductToCart.invoke(voucher) }
    }

    private fun thenProductsWereRetrieved() {
        assertEquals(Cart(products, emptyList()), viewModel.cart.value)
    }

    private fun thenConfirmThePurchase() {
        coVerify { confirmPurchase.invoke() }
    }

    companion object {
        val products = Products(listOf(ProductBuilder().tshirt().build()))
        val sameProducts = Products(listOf(ProductBuilder().tshirt().build()))
        val voucher = ProductBuilder().voucher().build()
    }
}

