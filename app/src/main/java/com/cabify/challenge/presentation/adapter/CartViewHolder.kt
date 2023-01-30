package com.cabify.challenge.presentation.adapter

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.cabify.challenge.R
import com.cabify.challenge.core.domain.cart.ProductOrder
import com.cabify.challenge.core.domain.products.Code
import com.cabify.challenge.databinding.ItemCartBinding

class CartViewHolder(
    view: View
) : ViewHolder(view) {

    private val binding = ItemCartBinding.bind(view)

    @SuppressLint("SetTextI18n")
    fun present(product: ProductOrder, quantity: Int) {
        binding.productDescription.text = "$quantity x ${product.name()}"
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