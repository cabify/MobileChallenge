package com.cabify.library.utils.extensions

import android.content.Context
import androidx.annotation.StringRes
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Context.displayDialog(
    @StringRes titleRes: Int,
    @StringRes contentRes: Int
) {
    MaterialAlertDialogBuilder(this).apply {
        setCancelable(true)
        setTitle(getString(titleRes))
        setMessage(getString(contentRes))
        create().show()
    }
}