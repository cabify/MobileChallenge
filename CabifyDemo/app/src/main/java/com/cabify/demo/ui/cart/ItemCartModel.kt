package com.cabify.demo.ui.cart

import android.util.Log
import com.cabify.demo.data.model.Product


abstract class ItemCartModel(open val cartItemProductData: Product) {
    open fun removeProductItemFromShoppingCart() {
        Log.d(this.toString(), "Product ${cartItemProductData.productId} was removed!")
    }
}