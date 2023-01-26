package com.cabify.challenge.presentation.store.views

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cabify.challenge.core.actions.GetProducts
import com.cabify.challenge.core.domain.products.Products
import kotlinx.coroutines.launch

class StoreViewModel(private val getProducts: GetProducts) : ViewModel() {

    private val _products = MutableLiveData<Products>()
    val products: LiveData<Products> = _products

    fun start() {
        viewModelScope.launch {
            val retrieveProducts = getProducts.invoke()
            _products.postValue(retrieveProducts)
        }
    }
}