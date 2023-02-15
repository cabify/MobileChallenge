package com.cabify.demo.ui.cart

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cabify.demo.data.model.Product
import com.cabify.demo.data.model.ProductDiscount
import kotlinx.coroutines.flow.*
import java.math.BigDecimal
import java.util.*

class ShoppingCartViewModel : ViewModel() {

    var shoppingCartItems = mutableStateListOf<ShoppingCartItem>()

    val shoppingCartTotalPriceState: State<BigDecimal>
        get() = mutableStateOf(shoppingCartItems.sumOf { it.cartItemProductData.amountTotal() })

    val isCtaButtonEnabledState: State<Boolean>
        get() = handleShoppingCartState().run { mutableStateOf(!shoppingCartItems.isEmpty()) }

    private val onShoppingCartStateEvent =
        mutableStateOf<ShoppingCartStates>(ShoppingCartStates.Initial)

    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog.asStateFlow()

    fun onOpenDialogClicked() {
        Log.d(this.toString(), "cashOut Button clicked")
        _showDialog.value = true
    }

    fun onDialogConfirm() {
        _showDialog.value = false
        shoppingCartItems = mutableStateListOf()
    }

    fun onDialogDismiss() {
        _showDialog.value = false
    }

    fun addItemToCart(code: String, name: String, price: BigDecimal) {
        shoppingCartItems.firstOrNull { x -> x.cartItemProductData.code == code }?.let {
            it.cartItemProductData.quantity += 1
        } ?: kotlin.run {
            shoppingCartItems.add(
                toShoppingCartItemViewModel(
                    UUID.randomUUID(), code, name, price, 1
                )
            )
        }

        Log.d(this.toString(), "Item added: " + code + " Cart Size: " + shoppingCartItems.size)
        Log.d(this.toString(), "Price :" + shoppingCartTotalPriceState.value.toString())
    }

    private fun handleShoppingCartState() =
        snapshotFlow { onShoppingCartStateEvent.value }.onEach { shoppingCartStateEvent ->
            when (shoppingCartStateEvent) {
                is ShoppingCartStates.Initial -> {
                    // nothing to do
                }
                is ShoppingCartStates.RemoveProductItemFromShoppingCartEvent -> {
                    removeProductItemFromShoppingCart(shoppingCartStateEvent.productId)
                }
            }
        }.launchIn(viewModelScope)

    private fun removeProductItemFromShoppingCart(productId: UUID) {
        shoppingCartItems.firstOrNull { it.cartItemProductData.productId == productId }?.also {
            shoppingCartItems.remove(it)
        }
    }

    private fun shoppingCartItemsFakeRepository() = listOf(
        toShoppingCartItemViewModel(
            UUID.randomUUID(),
            ProductDiscount.VOUCHER.name,
            ProductDiscount.VOUCHER.name,
            BigDecimal.valueOf(5.0),
            3
        ), toShoppingCartItemViewModel(
            UUID.randomUUID(),
            ProductDiscount.TSHIRT.name,
            ProductDiscount.TSHIRT.name,
            BigDecimal.valueOf(20.0),
            6
        ), toShoppingCartItemViewModel(
            UUID.randomUUID(),
            ProductDiscount.MUG.name,
            ProductDiscount.MUG.name,
            BigDecimal.valueOf(7.50),
            2
        )
    )

    private fun toShoppingCartItemViewModel(
        productId: UUID, code: String, name: String, price: BigDecimal, quantity: Int
    ) = ShoppingCartItem(
        cartItemProductData = Product(
            productId = productId,
            code = code,
            name = name,
            price = price,
            quantity = quantity,
        ), onShoppingCartStateEvent = onShoppingCartStateEvent
    )
}