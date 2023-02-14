package com.cabify.demo.ui

import androidx.compose.runtime.MutableState
import com.cabify.demo.data.model.Product

class ShoppingCartItemViewModel(
    override val cartItemProductData: Product,
    val onShoppingCartStateEvent: MutableState<ShoppingCartStates>,
) : CartItemSuperViewModel(cartItemProductData) {

    override fun removeProductItemFromShoppingCart() {
        super.removeProductItemFromShoppingCart()
        onShoppingCartStateEvent.value =
            ShoppingCartStates.RemoveProductItemFromShoppingCartEvent(cartItemProductData.productId)
    }
}


