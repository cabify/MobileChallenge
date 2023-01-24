package com.cabify.mobilechallenge.core.base.ui

import androidx.annotation.StringRes
import com.cabify.library.utils.extensions.displayDialog
import com.cabify.mobilechallenge.shared.commonui.R
import org.koin.androidx.scope.ScopeFragment

abstract class BaseFragment : ScopeFragment() {
    protected fun showErrorMessage(
        @StringRes titleRes: Int = R.string.general_error_title_message,
        @StringRes contentRes: Int = R.string.general_error_content_message
    ) {
        requireContext().displayDialog(
            titleRes = titleRes,
            contentRes = contentRes
        )
    }
}