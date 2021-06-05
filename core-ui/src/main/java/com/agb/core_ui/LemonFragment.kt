package com.agb.core_ui

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

abstract class LemonFragment : Fragment() {
    protected val lemonActivity: LemonActivity get() = activity as LemonActivity

    protected val router get() = lemonActivity.router

    protected fun shortToast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }

    protected fun shortToast(@StringRes text: Int) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }
}
