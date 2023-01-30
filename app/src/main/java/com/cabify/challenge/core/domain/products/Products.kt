package com.cabify.challenge.core.domain.products

data class Products(private val products: List<Product>) {
    fun getAllProducts(): List<Product> {
        return products
    }

    fun totalPrice(): Price {
        return if (products.isEmpty()) {
            totalIsZero()
        } else {
            addTheTotal()
        }
    }

    fun getProductsByCode(codeToMatch: Code): List<Product> {
        return products.filter { it.codeIs(codeToMatch) }
    }

    private fun addTheTotal(): Price {
        val total = products.map {
            it.price().amount()
        }.reduce { a, b ->
            a + b
        }
        return Price.eurPrice(total)
    }

    private fun totalIsZero() = Price.eurPrice(0.0)


}