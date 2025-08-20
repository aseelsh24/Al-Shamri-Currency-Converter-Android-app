package com.alshamri.currencyconverter.util

import com.alshamri.currencyconverter.data.local.CurrencyRate

object Constants {

    const val DATABASE_NAME = "currency_converter_db"

    // Currency Codes
    const val USD = "USD"
    const val SAR = "SAR"
    const val YER_OLD = "YER-Old"
    const val YER_NEW = "YER-New"
    const val GOLD = "Gold"

    // Default Rates (to USD)
    val DEFAULT_RATES = listOf(
        CurrencyRate(USD, "US Dollar", 1.0),
        CurrencyRate(SAR, "Saudi Riyal", 3.75),
        CurrencyRate(YER_OLD, "Old Yemeni Rial", 250.0),
        CurrencyRate(YER_NEW, "New Yemeni Rial", 1000.0),
        CurrencyRate(GOLD, "Gold Pound", 80.0)
    )

    // SharedPreferences Keys
    const val PREFS_NAME = "app_prefs"
    const val KEY_THEME = "key_theme"
    const val KEY_HISTORY = "key_history"
}
