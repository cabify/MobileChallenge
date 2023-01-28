package com.cabify.mobilechallenge.features.cart.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cabify.library.utils.recyclerview.DefaultItemCallback
import com.cabify.mobilechallenge.features.cart.R
import com.cabify.mobilechallenge.features.cart.presentation.model.OrderItemPresentation
import com.cabify.mobilechallenge.features.cart.presentation.model.OrderPresentation
import com.cabify.mobilechallenge.features.cart.presentation.model.OrderPricePresentation

class OrderItemsAdapter() :
    ListAdapter<OrderPresentation, RecyclerView.ViewHolder>(DefaultItemCallback() {
        it.hashCode() == it.hashCode()
    }) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is OrderItemPresentation -> ORDER_ITEM_VIEW_TYPE
        is OrderPricePresentation -> ORDER_PRICE_VIEW_TYPE
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        return when (viewType) {
            ORDER_ITEM_VIEW_TYPE -> {
                val view = inflater.inflate(R.layout.item_order_item, viewGroup)
                OrderItemViewHolder(view)
            }
            ORDER_PRICE_VIEW_TYPE -> {
                val view = inflater.inflate(R.layout.item_order_price, viewGroup)
                OrderItemViewHolder(view)
            }
            else -> throw java.lang.IllegalArgumentException("$viewType View type not declared")
        }
    }


    inner class OrderItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    inner class OrderPriceViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    companion object {
        private const val ORDER_ITEM_VIEW_TYPE = 1
        private const val ORDER_PRICE_VIEW_TYPE = 2
    }
}
