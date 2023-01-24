package com.cabify.mobilechallenge.features.home.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cabify.library.utils.recyclerview.DefaultItemCallback
import com.cabify.mobilechallenge.features.home.R
import com.cabify.mobilechallenge.features.home.databinding.ItemProductBinding
import com.cabify.mobilechallenge.features.home.presentation.model.ProductPresentation

class ProductsAdapter(private val onAddToCartClicked: ((String) -> Unit)? = null) :
    ListAdapter<ProductPresentation, ProductsAdapter.ViewHolder>(
        DefaultItemCallback(ProductPresentation::id)
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
                val productDrawable =
                    ContextCompat.getDrawable(
                        root.context,
                        com.cabify.mobilechallenge.shared.commonui.R.color.purple_200
                    )
                imageView.setImageDrawable(productDrawable)
                productName.text = item.name
                price.text = item.price
                availablePromotionName.text = item.availablePromotionName
                binding.addToCartButton.setOnClickListener {
                    onAddToCartClicked?.invoke(item.id)
                }
            }
        }
    }
}
