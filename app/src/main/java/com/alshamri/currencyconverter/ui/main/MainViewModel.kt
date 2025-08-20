package com.alshamri.currencyconverter.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alshamri.currencyconverter.data.local.CurrencyRate
import com.alshamri.currencyconverter.domain.repository.CurrencyRepository
import com.alshamri.currencyconverter.domain.repository.HistoryRepository
import com.alshamri.currencyconverter.domain.usecase.ConvertCurrencyUseCase
import com.alshamri.currencyconverter.util.Constants
import com.alshamri.currencyconverter.util.Formatter
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class MainScreenState(
    val amount: String = "1",
    val fromCurrencyCode: String = Constants.USD,
    val toCurrencyCode: String = Constants.SAR,
    val convertedAmount: Double? = null,
    val allRates: List<CurrencyRate> = emptyList()
)

class MainViewModel(
    private val currencyRepository: CurrencyRepository,
    private val historyRepository: HistoryRepository,
    private val convertCurrencyUseCase: ConvertCurrencyUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MainScreenState())
    val state: StateFlow<MainScreenState> = _state.asStateFlow()

    val history: Flow<List<String>> = historyRepository.getHistory()

    init {
        // Observe all rates from the repository
        viewModelScope.launch {
            currencyRepository.getAllRates().collect { rates ->
                _state.update { it.copy(allRates = rates) }
            }
        }

        // Reactive calculation
        viewModelScope.launch {
            combine(
                _state.map { it.amount }.distinctUntilChanged(),
                _state.map { it.fromCurrencyCode }.distinctUntilChanged(),
                _state.map { it.toCurrencyCode }.distinctUntilChanged(),
                _state.map { it.allRates }.distinctUntilChanged()
            ) { amountStr, fromCode, toCode, rates ->
                performConversion(amountStr, fromCode, toCode, rates)
            }.collect { result ->
                _state.update { it.copy(convertedAmount = result) }
            }
        }
    }

    private fun performConversion(amountStr: String, fromCode: String, toCode: String, rates: List<CurrencyRate>): Double? {
        val amount = amountStr.toDoubleOrNull() ?: 0.0
        val fromRate = rates.find { it.currencyCode == fromCode }?.rate
        val toRate = rates.find { it.currencyCode == toCode }?.rate

        if (fromRate == null || toRate == null) {
            return null
        }

        return convertCurrencyUseCase(amount, fromRate, toRate)
    }

    fun onAmountChange(newAmount: String) {
        _state.update { it.copy(amount = newAmount) }
    }

    fun onFromCurrencyChange(newFromCurrencyCode: String) {
        _state.update { it.copy(fromCurrencyCode = newFromCurrencyCode) }
    }

    fun onToCurrencyChange(newToCurrencyCode: String) {
        _state.update { it.copy(toCurrencyCode = newToCurrencyCode) }
    }

    fun swapCurrencies() {
        val currentState = _state.value
        _state.update {
            it.copy(
                fromCurrencyCode = currentState.toCurrencyCode,
                toCurrencyCode = currentState.fromCurrencyCode
            )
        }
    }

    fun saveCurrentConversion() {
        viewModelScope.launch {
            val s = _state.value
            val amount = s.amount.toDoubleOrNull() ?: return@launch
            if (amount == 0.0) return@launch

            val result = s.convertedAmount ?: return@launch

            val fromCurrency = s.allRates.find { it.currencyCode == s.fromCurrencyCode } ?: return@launch
            val toCurrency = s.allRates.find { it.currencyCode == s.toCurrencyCode } ?: return@launch

            val conversionText = "${Formatter.formatAmount(amount)} ${fromCurrency.currencyCode} → ${Formatter.formatAmount(result)} ${toCurrency.currencyCode}"
            historyRepository.addConversionToHistory(conversionText)
        }
    }
}
