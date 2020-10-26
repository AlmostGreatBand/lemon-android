package com.agb.lemon_android.frameworks.datasource.local

import android.content.Context
import androidx.preference.PreferenceManager

class PreferencesManager(context: Context) {
    private val preference = PreferenceManager.getDefaultSharedPreferences(context)

    fun getString(name: String): String? = preference.getString(name, null)
    fun putString(name: String, value: String?) {
        preference
            .edit()
            .putString(name, value)
            .apply()
    }
}
