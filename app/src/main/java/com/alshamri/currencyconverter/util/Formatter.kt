package com.alshamri.currencyconverter.util

import java.text.DecimalFormat

object Formatter {

    private val formatter = DecimalFormat("#,##0.####")

    fun formatAmount(amount: Double?): String {
        if (amount == null) return ""
        return formatter.format(amount)
    }
}
