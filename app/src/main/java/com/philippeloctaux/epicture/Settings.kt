package com.philippeloctaux.epicture

import android.content.Context
import android.content.SharedPreferences

class Settings constructor(context: Context) {

    /// List of elements in settings
    val accessToken = "access_token"
    val expiresIn = "expires_in"
    val tokenType = "token_type"
    val refreshToken = "refresh_token"
    val accountUsername = "account_username"
    val accountId = "account_id"

    /// Android preferences system
    private val settingsName = "epicture_settings"
    private val settings: SharedPreferences =
        context.getSharedPreferences(settingsName, Context.MODE_PRIVATE)
    private val settingsEditor: SharedPreferences.Editor = settings.edit()

    /**
     * Clear settings
     * Example: `clear()`
     */
    fun clear() {
        settingsEditor.clear().commit()
    }

    /**
     * Set value of key
     * Example: `setKeyValue(settings.accountUsername, "phil")`
     */
    fun setKeyValue(key: String, value: String) {
        settingsEditor.putString(key, value)
        settingsEditor.commit()
    }

    /**
     * Get value of key
     * Example: `getValue(settings.accountUsername)`
     */
    fun getValue(key: String): String? {
        return settings.getString(key, "")
    }
}