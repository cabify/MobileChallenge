package com.cabify.demo.ui.cart

import androidx.compose.runtime.MutableState
import com.cabify.demo.data.model.Product

class ShoppingCartItem(
    override val cartItemProductData: Product,
    val onShoppingCartStateEvent: MutableState<ShoppingCartStates>,
) : ItemCartModel(cartItemProductData) {

    override fun removeProductItemFromShoppingCart() {
        super.removeProductItemFromShoppingCart()
        onShoppingCartStateEvent.value =
            ShoppingCartStates.RemoveProductItemFromShoppingCartEvent(cartItemProductData.productId)
    }
}


