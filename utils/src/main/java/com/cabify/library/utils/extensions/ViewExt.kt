package com.cabify.library.utils.extensions

import android.os.SystemClock
import android.view.View

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun debounceOnClickListener(
    debounceTime: Long = 500L,
    action: (View) -> Unit
) = object : View.OnClickListener {
    private var lastClickTime: Long = 0

    override fun onClick(v: View) {
        if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) return
        else action.invoke(v)

        lastClickTime = SystemClock.elapsedRealtime()
    }
}

fun View.debounceClick(debounceTime: Long = 500L, action: (View) -> Unit) {
    this.setOnClickListener(debounceOnClickListener(debounceTime, action))
}