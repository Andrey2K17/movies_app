package com.pg13.moviesapp.utils

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pg13.moviesapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

inline fun LifecycleOwner.launchOnLifecycle(
    state: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: suspend (CoroutineScope) -> Unit,
): Job {
    return lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(state) {
            block.invoke(this)
        }
    }
}

fun Context.showErrorDialog(message: String) {
    MaterialAlertDialogBuilder(this, R.style.ThemeOverlay_MaterialAlertDialog).apply {
        setTitle(R.string.dialog_title_error)
        setMessage(message)
        setPositiveButton(R.string.dialog_button_ok) { dialog, which ->
            dialog.dismiss()
        }
    }.show()
}

fun Fragment.showErrorDialog(message: String) = requireContext().showErrorDialog(message)

fun Any?.toStringOrEmpty() = this?.toString() ?: ""