package com.alshamri.currencyconverter.util

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager

class ThemeManager(private val context: Context) {

    enum class ThemeMode {
        LIGHT, DARK, SYSTEM
    }

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun applyTheme() {
        val theme = getTheme()
        val mode = when (theme) {
            ThemeMode.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
            ThemeMode.DARK -> AppCompatDelegate.MODE_NIGHT_YES
            ThemeMode.SYSTEM -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
        AppCompatDelegate.setDefaultNightMode(mode)
    }

    fun getTheme(): ThemeMode {
        val themeStr = sharedPreferences.getString(Constants.KEY_THEME, ThemeMode.SYSTEM.name)
        return ThemeMode.valueOf(themeStr ?: ThemeMode.SYSTEM.name)
    }

    fun setTheme(theme: ThemeMode) {
        sharedPreferences.edit().putString(Constants.KEY_THEME, theme.name).apply()
        applyTheme()
    }
}
