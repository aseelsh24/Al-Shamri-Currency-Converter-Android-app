package com.alshamri.currencyconverter.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_rates")
data class CurrencyRate(
    @PrimaryKey
    val currencyCode: String,
    val currencyName: String,
    val rate: Double
)
