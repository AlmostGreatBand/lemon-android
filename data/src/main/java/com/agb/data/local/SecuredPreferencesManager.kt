package com.agb.data.local

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class SecuredPreferencesManager(
    context: Context,
    fileName: String = "user_data"
) {
    private val prefs = EncryptedSharedPreferences.create(
        fileName,
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    operator fun get(key: String): String? = prefs.getString(key, null)

    operator fun set(key: String, value: String): Unit = with(prefs.edit()) {
        putString(key, value)
        apply()
    }

    operator fun minusAssign(key: String): Unit = with(prefs.edit()) {
        remove(key)
        apply()
    }

    fun clear(): Unit = with(prefs.edit()) {
        clear()
        apply()
    }
}
