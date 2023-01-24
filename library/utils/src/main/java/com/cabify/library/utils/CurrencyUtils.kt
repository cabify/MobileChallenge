package com.cabify.library.utils

import android.icu.util.Currency
import java.util.Locale

class CurrencyUtils {
    fun getPriceWithCurrencySymbol(price: Double): String =
        String.format("%.2f", price) + Currency.getInstance(Locale.getDefault()).symbol
}