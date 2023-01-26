package com.cabify.challenge.core.domain.products


import junit.framework.TestCase.assertEquals
import org.junit.Test

class PriceShould {

    @Test(expected = NegativeCurrencyAmountException::class)
    fun `not create a price with negative amount`() {
        Price(-1.0)
    }

    @Test(expected = NegativeCurrencyAmountException::class)
    fun `not create a eur price with negative amount`() {
        Price.eurPrice(-1.0)
    }

    @Test
    fun `create a price with zero amount`() {
        val price = Price.eurPrice(0.0)

        assertEquals(Price(0.0), price)
    }

    @Test
    fun `create a price with positive amount`() {
        val price = Price.eurPrice(100.0)

        assertEquals(Price(100.0), price)
    }

    @Test
    fun `retrieve amount`() {
        val price = Price.eurPrice(100.0)

        val amount = price.amount()

        assertEquals(100.0, amount)
    }

}