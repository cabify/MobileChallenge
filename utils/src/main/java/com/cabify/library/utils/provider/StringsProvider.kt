package com.cabify.library.utils.provider

import androidx.annotation.PluralsRes
import androidx.annotation.StringRes

interface StringsProvider {
    fun getString(resId: Int): String
    fun getString(@StringRes resId: Int, vararg formatArgs: Any?): String
    fun getPlural(@PluralsRes resId: Int, quantity:Int,vararg formatArgs: Any?): String
}