package com.cabify.challenge.presentation.store.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cabify.challenge.R
import com.cabify.challenge.core.domain.products.Product

class CartAdapter(
    private val products: List<Product>
) : RecyclerView.Adapter<CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CartViewHolder(
            layoutInflater.inflate(
                R.layout.item_products, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = products[position]
        holder.present(item)
    }

    override fun getItemCount(): Int {
        return products.size
    }
}