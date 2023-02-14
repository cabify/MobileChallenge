package com.cabify.demo.ui.utils

import android.icu.util.Currency
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.cabify.demo.R
import java.math.BigDecimal
import java.util.*

object AlertDialog {

    @RequiresApi(Build.VERSION_CODES.N)
    @Composable
    fun StartCashOutProcess(
        show: Boolean,
        onDismiss: () -> Unit,
        onConfirm: () -> Unit,
        totalPrice: BigDecimal
    ) {
        if (show) {
            AlertDialog(onDismissRequest = onDismiss, title = {
                Text(text = stringResource(R.string.payment))
            }, text = {
                val symbol = Currency.getInstance(Locale.getDefault()).symbol
                Text(stringResource(id = R.string.payment_dialog_text, totalPrice, symbol))
            }, confirmButton = {
                Button(
                    onClick = onConfirm
                ) {
                    Text(stringResource(R.string.pay))
                }
            }, dismissButton = {
                Button(
                    onClick = onDismiss
                ) {
                    Text(stringResource(R.string.cancel))
                }
            })
        }
    }
}