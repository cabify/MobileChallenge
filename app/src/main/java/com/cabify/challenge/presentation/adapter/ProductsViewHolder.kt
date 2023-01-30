package com.cabify.challenge.presentation.adapter

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.cabify.challenge.R
import com.cabify.challenge.core.domain.products.Code
import com.cabify.challenge.core.domain.products.Product
import com.cabify.challenge.databinding.ItemProductsBinding

class ProductsViewHolder(
    private val view: View,
    private val onAddToCart: (product: Product) -> Unit
) : ViewHolder(view) {

    private val binding = ItemProductsBinding.bind(view)

    fun present(product: Product) {
        makeDescriptionCard(product)
        makeOnAddToCard(product)
    }

    private fun makeOnAddToCard(product: Product) {
        binding.addToCart.setOnClickListener {
            onAddToCart(product)
            makeToastWith(product.name())
        }
    }

    private fun makeDescriptionCard(product: Product) {
        binding.productDescription.text = product.name()
        binding.productPrice.text = product.presentPrice()

        when (product.code()) {
            Code.MUG -> {
                binding.productImage.setImageResource(R.mipmap.coffee)
            }
            Code.TSHIRT -> {
                binding.productImage.setImageResource(R.mipmap.tshirt)
            }
            Code.VOUCHER -> {
                binding.productImage.setImageResource(R.mipmap.ticket)
            }
        }
    }

    private fun makeToastWith(productName: String) {
        Toast.makeText(view.context, "$productName added to cart", Toast.LENGTH_SHORT)
            .show()
    }

}