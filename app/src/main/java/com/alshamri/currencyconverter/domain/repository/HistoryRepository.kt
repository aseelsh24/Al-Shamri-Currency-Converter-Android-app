package com.alshamri.currencyconverter.domain.repository

import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    fun getHistory(): Flow<List<String>>
    suspend fun addConversionToHistory(conversion: String)
}
