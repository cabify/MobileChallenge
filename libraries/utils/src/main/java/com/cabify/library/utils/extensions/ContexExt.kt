package com.cabify.library.utils.extensions

import android.content.Context
import android.view.Gravity
import android.widget.Toast
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

fun Context.showToast(@StringRes stringRes: Int) {
    Toast.makeText(applicationContext, getString(stringRes), Toast.LENGTH_SHORT)
        .show()
}