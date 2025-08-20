package com.alshamri.currencyconverter.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alshamri.currencyconverter.data.local.CurrencyRate
import com.alshamri.currencyconverter.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val currencyRepository: CurrencyRepository
) : ViewModel() {

    val rates: StateFlow<List<CurrencyRate>> = currencyRepository.getAllRates()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun updateRates(updatedRates: Map<String, Double>) {
        viewModelScope.launch {
            updatedRates.forEach { (code, rate) ->
                currencyRepository.updateRate(code, rate)
            }
        }
    }
}
