package com.alshamri.currencyconverter.domain.repository

import com.alshamri.currencyconverter.data.local.CurrencyRate
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {

    fun getAllRates(): Flow<List<CurrencyRate>>

    suspend fun updateRate(currencyCode: String, newRate: Double)

    suspend fun getRate(code: String): CurrencyRate?

}
