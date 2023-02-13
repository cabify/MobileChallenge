package com.cabify.demo.data.model

data class Cart(
    val items: List<Product> = emptyList(), var amount: Double
) {

    fun updateAmount() {
        var amount = 0.0

        amount += ((items.count { x -> x.code == "VOUCHER" } / 2) * 5.00)
        amount += if (items.count { x -> x.code == "VOUCHER" } % 2 == 1) {
            5
        } else {
            0
        }

        amount += if (items.count { x -> x.code == "TSHIRT" } > 2) {
            items.count { x -> x.code == "TSHIRT" } * 19
        } else {
            20
        }

        amount += items.count { x -> x.code == "MUG" } * 7.5

        this.amount = amount
    }
}