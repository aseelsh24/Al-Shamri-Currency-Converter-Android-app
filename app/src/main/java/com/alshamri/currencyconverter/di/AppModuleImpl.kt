package com.alshamri.currencyconverter.di

import android.app.Application
import android.app.Application
import com.alshamri.currencyconverter.data.local.AppDatabase
import com.alshamri.currencyconverter.data.repository.CurrencyRepositoryImpl
import com.alshamri.currencyconverter.data.repository.HistoryRepositoryImpl
import com.alshamri.currencyconverter.domain.repository.CurrencyRepository
import com.alshamri.currencyconverter.domain.repository.HistoryRepository
import com.alshamri.currencyconverter.domain.usecase.ConvertCurrencyUseCase

class AppModuleImpl(
    private val application: Application
) : AppModule {

    private val appDatabase: AppDatabase by lazy {
        AppDatabase.getDatabase(application)
    }

    override val currencyRepository: CurrencyRepository by lazy {
        CurrencyRepositoryImpl(appDatabase.currencyDao())
    }

    override val historyRepository: HistoryRepository by lazy {
        HistoryRepositoryImpl(application)
    }

    override val convertCurrencyUseCase: ConvertCurrencyUseCase by lazy {
        ConvertCurrencyUseCase()
    }
}
