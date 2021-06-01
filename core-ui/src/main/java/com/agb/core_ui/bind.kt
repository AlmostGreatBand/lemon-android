package com.agb.core_ui

import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import kotlinx.coroutines.flow.MutableStateFlow

fun <T> EditText.bindTo(flow: MutableStateFlow<T>, transformer: (String?) -> T) {
    addTextChangedListener { flow.value = transformer(it?.toString()) }
}

@JvmName("bindStringNullable")
infix fun EditText.bindTo(flow: MutableStateFlow<String?>) = bindTo(flow) { it }

infix fun EditText.bindTo(flow: MutableStateFlow<String>) = bindTo(flow) { it ?: "" }
