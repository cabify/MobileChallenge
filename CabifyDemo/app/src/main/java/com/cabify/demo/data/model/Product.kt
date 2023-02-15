package com.cabify.demo.data.model

import java.math.BigDecimal
import java.util.*

data class Product(
    val productId: UUID = UUID.randomUUID(),
    val code: String = String(),
    val name: String = String(),
    val price: BigDecimal = BigDecimal.ZERO,
    var quantity: Int = 1
) {

    fun discount(): Boolean {
        return when (code) {
            ProductDiscount.VOUCHER.name -> {
                quantity > 1
            }
            ProductDiscount.TSHIRT.name -> {
                quantity > 2
            }
            else -> {
                false
            }
        }
    }

    fun discountValue(): BigDecimal {
        return (price * quantity.toBigDecimal()).minus(amountTotal())
    }

    //This method contains the price and the discount pattern of each product
    fun amountUnit(): BigDecimal {
        when (code) {
            ProductDiscount.VOUCHER.name -> {
                var amount = BigDecimal.ZERO

                amount += price.multiply(BigDecimal(quantity / 2))
                amount += if (quantity % 2 == 1) {
                    price
                } else {
                    BigDecimal.ZERO
                }

                return amount
            }
            ProductDiscount.TSHIRT.name -> {
                return if (quantity > 2) {
                    BigDecimal.valueOf(19.0)
                } else {
                    price
                }
            }
            else -> {
                return price
            }
        }
    }

    fun amountTotal(): BigDecimal {
        return when (code) {
            ProductDiscount.VOUCHER.name -> {
                amountUnit()
            }
            ProductDiscount.TSHIRT.name -> {
                amountUnit().multiply(quantity.toBigDecimal())
            }
            else -> {
                price.multiply(quantity.toBigDecimal())
            }
        }
    }
}

enum class ProductDiscount {
    TSHIRT, VOUCHER, MUG
}