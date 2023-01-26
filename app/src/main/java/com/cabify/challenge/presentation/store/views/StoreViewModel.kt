package com.cabify.challenge.presentation.store.views

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cabify.challenge.core.actions.GetProducts
import com.cabify.challenge.core.domain.products.Products

class StoreViewModel(getProducts: GetProducts) : ViewModel() {
    private val _products = MutableLiveData<Products>()
    val products: LiveData<Products> = _products


    init {
        val retrieveProducts = getProducts()
        _products.value = retrieveProducts
    }
}