package com.cabify.challenge.presentation.store.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.cabify.challenge.R
import com.cabify.challenge.core.domain.products.Code
import com.cabify.challenge.core.domain.products.Product

class ProductsViewHolder(view: View) : ViewHolder(view) {

    private val productDescription: TextView = view.findViewById(R.id.productDescription)
    private val productPrice: TextView = view.findViewById(R.id.productPrice)
    private val productImage: ImageView = view.findViewById(R.id.productImage)
    fun present(product: Product) {
        productDescription.text = product.name()
        productPrice.text = product.presentPrice()
        setImageByCode(product.code())
    }

    private fun setImageByCode(code: Code) {
        when (code) {
            Code.MUG -> {
                productImage.setImageResource(R.mipmap.coffee)
            }
            Code.TSHIRT -> {
                productImage.setImageResource(R.mipmap.tshirt)
            }
            Code.VOUCHER -> {
                productImage.setImageResource(R.mipmap.ticket)
            }
        }
    }
}