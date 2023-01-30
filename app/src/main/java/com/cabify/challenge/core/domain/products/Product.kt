package com.cabify.challenge.core.domain.products

data class Product(
    private val code: Code,
    private val name: String,
    private val price: Price
) {
    fun price(): Price {
        return price
    }

    fun presentPrice(): String {
        return price.presentPrice()
    }

    fun name(): String {
        return name
    }

    fun code(): Code {
        return code
    }


    fun codeIs(code: Code): Boolean {
        return this.code == code
    }

}