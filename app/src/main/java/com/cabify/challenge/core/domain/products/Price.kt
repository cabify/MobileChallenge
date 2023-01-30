package com.cabify.challenge.core.domain.products

data class Price(
    private val amount: Double,
    private val currency: Currency = Currency.EUR
) {
    init {
        if (amount < 0) {
            throw NegativeCurrencyAmountException()
        }
    }

    fun amount(): Double {
        return amount
    }

    fun presentPrice(): String {
        return "${amount}${currency.symbol}"
    }

    companion object {
        fun eurPrice(amount: Double): Price {
            return Price(amount)
        }
    }

}

class NegativeCurrencyAmountException :
    Throwable(message = "Currency have to be greather than zero")