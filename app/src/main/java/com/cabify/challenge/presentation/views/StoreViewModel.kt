package com.cabify.challenge.presentation.views

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cabify.challenge.core.actions.AddProductToCart
import com.cabify.challenge.core.actions.ConfirmPurchase
import com.cabify.challenge.core.actions.GetProducts
import com.cabify.challenge.core.actions.GetProductsFromCart
import com.cabify.challenge.core.domain.cart.Cart
import com.cabify.challenge.core.domain.products.Product
import com.cabify.challenge.core.domain.products.Products
import kotlinx.coroutines.launch

class StoreViewModel(
    private val getProducts: GetProducts,
    private val addProductToCart: AddProductToCart,
    private val getProductsFromCart: GetProductsFromCart,
    private val confirmPurchase: ConfirmPurchase
) : ViewModel() {

    private val _products = MutableLiveData<Products>()
    val products: LiveData<Products> = _products

    private val _cart = MutableLiveData<Cart>()
    val cart: LiveData<Cart> = _cart

    fun start() {
        viewModelScope.launch {
            val retrieveProducts = getProducts.invoke()
            _products.postValue(retrieveProducts)
        }
    }

    fun onAddToCart(product: Product) {
        viewModelScope.launch {
            addProductToCart(product)
        }
    }

    fun onGetProductFromCart() {
        viewModelScope.launch {
            val retrieveProducts = getProductsFromCart.invoke()
            _cart.postValue(retrieveProducts)
        }
    }

    fun onConfirmPurchase() {
        viewModelScope.launch {
            confirmPurchase.invoke()
        }
    }


}