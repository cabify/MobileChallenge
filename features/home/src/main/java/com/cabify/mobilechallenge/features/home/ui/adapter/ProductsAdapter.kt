package com.cabify.mobilechallenge.features.home.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cabify.library.utils.extensions.gone
import com.cabify.library.utils.extensions.visible
import com.cabify.library.utils.recyclerview.DiffUtilDefaultItemCallback
import com.cabify.mobilechallenge.features.home.R
import com.cabify.mobilechallenge.features.home.databinding.ItemProductBinding
import com.cabify.mobilechallenge.features.home.presentation.model.ProductPresentation

class ProductsAdapter(private val onAddToCartClicked: ((String) -> Unit)? = null) :
    ListAdapter<ProductPresentation, ProductsAdapter.ViewHolder>(
        DiffUtilDefaultItemCallback(
            ProductPresentation::id
        )
    ) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_product, viewGroup, false)
        return ViewHolder(view)
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemProductBinding.bind(view)
        fun bind(item: ProductPresentation) {
            with(binding) {
                productName.text = item.name
                unitPrice.text = item.price
                addToCartButton.setOnClickListener {
                    onAddToCartClicked?.invoke(item.id)
                }
                if (item.availablePromotionName == null) {
                    promotionName.gone()
                } else {
                    promotionName.visible()
                    promotionName.text = item.availablePromotionName
                }
            }
        }
    }
}
