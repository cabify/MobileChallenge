package com.cabify.library.utils.provider

import android.content.Context

class StringsProviderImpl(private val applicationContext: Context) : StringsProvider {
    override fun getString(resId: Int): String = applicationContext.getString(resId)
    override fun getString(resId: Int, vararg formatArgs: Any?): String = applicationContext.getString(resId, *formatArgs)
}