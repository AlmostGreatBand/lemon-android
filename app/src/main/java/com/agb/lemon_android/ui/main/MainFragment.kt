package com.agb.lemon_android.ui.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.agb.lemon_android.R
import com.agb.lemon_android.ui.LemonFragment
import com.agb.lemon_android.ui.MainActivity
import com.agb.lemon_android.ui.registration.SignUpFragment
import kotlinx.android.synthetic.main.login_fragment.*

class MainFragment : LemonFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }
}
