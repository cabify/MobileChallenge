package com.cabify.demo.ui.cart

import java.util.*

sealed class ShoppingCartStates {
    object Initial : ShoppingCartStates()
    data class RemoveProductItemFromShoppingCartEvent(val productId: UUID) : ShoppingCartStates()
}