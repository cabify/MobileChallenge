package com.cabify.library.utils.extensions

import android.graphics.Paint
import android.widget.TextView

fun TextView.strikeThrough() {
    this.paintFlags = this.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
}