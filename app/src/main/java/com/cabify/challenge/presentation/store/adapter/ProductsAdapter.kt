package com.cabify.challenge.presentation.store.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cabify.challenge.R
import com.cabify.challenge.core.domain.products.Product

class ProductsAdapter(
    private val products: List<Product>,
    private val onAddToCart: (product: Product) -> Unit = {}
) : RecyclerView.Adapter<ProductsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ProductsViewHolder(layoutInflater.inflate(
            R.layout.item_products, parent, false)) {
            onAddToCart(it)
        }
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val item = products[position]
        holder.present(item)
    }

    override fun getItemCount(): Int {
        return products.size
    }
}