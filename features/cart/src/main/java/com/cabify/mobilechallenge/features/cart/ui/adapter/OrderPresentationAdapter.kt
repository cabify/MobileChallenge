package com.cabify.mobilechallenge.features.cart.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cabify.library.utils.recyclerview.DiffUtilDefaultItemCallback
import com.cabify.mobilechallenge.features.cart.R
import com.cabify.mobilechallenge.features.cart.databinding.ItemOrderItemBinding
import com.cabify.mobilechallenge.features.cart.databinding.ItemOrderPriceBinding
import com.cabify.mobilechallenge.features.cart.presentation.model.OrderItemPresentation
import com.cabify.mobilechallenge.features.cart.presentation.model.OrderPresentation
import com.cabify.mobilechallenge.features.cart.presentation.model.OrderPricePresentation

class OrderPresentationAdapter :
    ListAdapter<OrderPresentation, RecyclerView.ViewHolder>(orderPresentationDiffUtilItemCallback()) {
    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is OrderItemPresentation -> ORDER_ITEM_VIEW_TYPE
        is OrderPricePresentation -> ORDER_PRICE_VIEW_TYPE
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is OrderItemViewHolder -> holder.bind(item as OrderItemPresentation)
            is OrderPriceViewHolder -> holder.bind(item as OrderPricePresentation)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        return when (viewType) {
            ORDER_ITEM_VIEW_TYPE -> {
                val view = inflater.inflate(R.layout.item_order_item, viewGroup, false)
                OrderItemViewHolder(view)
            }
            ORDER_PRICE_VIEW_TYPE -> {
                val view = inflater.inflate(R.layout.item_order_price, viewGroup, false)
                OrderPriceViewHolder(view)
            }
            else -> throw java.lang.IllegalArgumentException("$viewType -> View type not declared")
        }
    }


    inner class OrderItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: ItemOrderItemBinding = ItemOrderItemBinding.bind(view)
        fun bind(item: OrderItemPresentation) {
            with(binding) {
                productName.text = item.productName
                //binding.productImage.drawable = ContextCompat.getDrawable(binding.root.context,R.drawable)
                productPrice.text = item.itemPrice
                productQuantity.text = item.quantity //TODO add string with x
                promotionName.text = item.promotionPresentation?.promotionName
                productPromotionInfo.text = item.promotionPresentation?.promotionInfo
                productSubtotal.text = item.subtotalPrice
            }
        }
    }

    inner class OrderPriceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: ItemOrderPriceBinding = ItemOrderPriceBinding.bind(view)

        fun bind(item: OrderPricePresentation) {
            with(binding) {
                baseTotalPrice.text = item.baseTotalPrice
                promotionsDiscountPrice.text = item.promotionDiscountedPrice
                finalTotalPrice.text = item.totalPrice
            }
        }
    }

    companion object {
        private const val ORDER_ITEM_VIEW_TYPE = 1
        private const val ORDER_PRICE_VIEW_TYPE = 2
    }
}

private fun orderPresentationDiffUtilItemCallback(): DiffUtilDefaultItemCallback<OrderPresentation> =
    DiffUtilDefaultItemCallback {
        when (it) {
            is OrderItemPresentation -> it.productId
            is OrderPricePresentation -> it.orderId
        }
    }
