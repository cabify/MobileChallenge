package com.cabify.challenge.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cabify.challenge.R
import com.cabify.challenge.core.domain.cart.Cart

class CartAdapter(
    private val cart: Cart
) : RecyclerView.Adapter<CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CartViewHolder(
            layoutInflater.inflate(
                R.layout.item_cart, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val productsOrder = cart.getProductsOrder()
        val item = productsOrder[position]
        holder.present(item, cart.getItemsQuantityByCode(item.code()))
    }

    override fun getItemCount(): Int {
        return cart.getProductsOrder().size
    }
}