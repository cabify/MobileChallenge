package com.cabify.challenge.presentation.store.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.cabify.challenge.R
import com.cabify.challenge.core.domain.products.Code
import com.cabify.challenge.core.domain.products.Product
import com.cabify.challenge.databinding.ItemProductsBinding

class CartViewHolder(
    view: View
) : ViewHolder(view) {

    private val binding = ItemProductsBinding.bind(view)
    fun present(product: Product) {
        binding.productDescription.text = product.name()
        binding.productPrice.text = product.presentPrice()
        setImageByCode(product.code())
    }


    private fun setImageByCode(code: Code) {
        when (code) {
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
}