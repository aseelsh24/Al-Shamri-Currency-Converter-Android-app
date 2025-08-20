package com.alshamri.currencyconverter.di

import com.alshamri.currencyconverter.domain.repository.CurrencyRepository
import com.alshamri.currencyconverter.domain.repository.HistoryRepository
import com.alshamri.currencyconverter.domain.usecase.ConvertCurrencyUseCase

interface AppModule {
    val currencyRepository: CurrencyRepository
    val historyRepository: HistoryRepository
    val convertCurrencyUseCase: ConvertCurrencyUseCase
}
