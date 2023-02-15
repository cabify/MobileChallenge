package com.cabify.demo.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cabify.demo.data.repository.ProductRepository
import com.cabify.demo.ui.HomeUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val productRepository: ProductRepository
) : ViewModel() {

    // UI state exposed to the UI
    private val _uiState = MutableStateFlow(HomeUIState(loading = true))
    val uiState: StateFlow<HomeUIState> = _uiState

    init {
        observeProducts()
    }

    private fun observeProducts() {
        viewModelScope.launch {
            productRepository.getProducts().catch { ex ->
                _uiState.value = HomeUIState(error = ex.message)
                Log.d(this.toString(), ex.message.toString())
                val prods = productRepository.getLocalProducts().products
                _uiState.value = HomeUIState(
                    products = prods
                )
            }.collect { products ->
                _uiState.value = HomeUIState(
                    products = products.products
                )
            }
        }
    }
}
