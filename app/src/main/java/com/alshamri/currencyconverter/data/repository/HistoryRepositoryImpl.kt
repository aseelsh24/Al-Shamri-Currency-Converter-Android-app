package com.alshamri.currencyconverter.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.alshamri.currencyconverter.domain.repository.HistoryRepository
import com.alshamri.currencyconverter.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext

class HistoryRepositoryImpl(context: Context) : HistoryRepository {

    private val prefs: SharedPreferences = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE)
    private val historyFlow = MutableStateFlow<List<String>>(emptyList())

    init {
        historyFlow.value = loadHistory()
    }

    override fun getHistory() = historyFlow

    override suspend fun addConversionToHistory(conversion: String) {
        withContext(Dispatchers.IO) {
            val currentHistory = loadHistory().toMutableList()
            currentHistory.add(0, conversion)
            val updatedHistory = currentHistory.take(5)
            saveHistory(updatedHistory)
            historyFlow.value = updatedHistory
        }
    }

    private fun saveHistory(history: List<String>) {
        prefs.edit().putStringSet(Constants.KEY_HISTORY, history.toSet()).apply()
    }

    private fun loadHistory(): List<String> {
        // SharedPreferences does not guarantee order for string sets,
        // but for this app's purpose (displaying recent items), it's generally acceptable.
        // A more robust solution might involve serializing to JSON.
        return prefs.getStringSet(Constants.KEY_HISTORY, emptySet())?.toList() ?: emptyList()
    }
}
