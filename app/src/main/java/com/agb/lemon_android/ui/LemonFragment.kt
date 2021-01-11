package com.agb.lemon_android.ui

import android.widget.Toast
import androidx.fragment.app.Fragment

abstract class LemonFragment : Fragment() {
    protected fun createToast(msg: String) = Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

    protected fun changeFragment(fragment: Fragment) {
        (requireActivity() as MainActivity).changeFragment(fragment)
    }
}