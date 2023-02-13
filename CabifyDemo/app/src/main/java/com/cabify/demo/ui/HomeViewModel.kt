package com.cabify.demo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cabify.demo.data.model.Product
import com.cabify.demo.data.repository.ProductRepository
import com.cabify.demo.data.repository.ProductRepositoryImpl
import com.cabify.demo.ui.utils.ContentType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val productRepository: ProductRepository = ProductRepositoryImpl(),
) : ViewModel() {

    // UI state exposed to the UI
    private val _uiState = MutableStateFlow(HomeUIState(loading = true))
    val uiState: StateFlow<HomeUIState> = _uiState

    init {
        observeEmails()
    }

    private fun observeEmails() {
        viewModelScope.launch {
            productRepository.getProducts().catch { ex ->
                _uiState.value = HomeUIState(error = ex.message)
            }.collect { products ->
                /**
                 * We set first product selected by default for first App launch in large-screens
                 */
                _uiState.value = HomeUIState(
                    products = products.products, selectedProduct = products.products.firstOrNull()
                )
            }
        }
    }

    fun setSelectedEmail(productId: String, contentType: ContentType) {
        /**
         * We only set isDetailOnlyOpen to true when it's only single pane layout
         */
        val product = uiState.value.products.find { it.code == productId }
        _uiState.value = _uiState.value.copy(
            selectedProduct = product, isDetailOnlyOpen = contentType == ContentType.SINGLE_PANE
        )
    }

    fun closeDetailScreen() {
        _uiState.value = _uiState.value.copy(
            isDetailOnlyOpen = false, selectedProduct = _uiState.value.products.firstOrNull()
        )
    }
}

data class HomeUIState(
    val products: List<Product> = emptyList(),
    val selectedProduct: Product? = null,
    val isDetailOnlyOpen: Boolean = false,
    val loading: Boolean = false,
    val error: String? = null
)
