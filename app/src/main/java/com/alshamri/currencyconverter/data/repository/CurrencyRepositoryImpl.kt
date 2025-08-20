package com.alshamri.currencyconverter.data.repository

import com.alshamri.currencyconverter.data.local.CurrencyDao
import com.alshamri.currencyconverter.data.local.CurrencyRate
import com.alshamri.currencyconverter.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CurrencyRepositoryImpl(
    private val dao: CurrencyDao
) : CurrencyRepository {

    override fun getAllRates(): Flow<List<CurrencyRate>> {
        return dao.getAllRates()
    }

    override suspend fun updateRate(currencyCode: String, newRate: Double) {
        dao.updateRate(currencyCode, newRate)
    }

    override suspend fun getRate(code: String): CurrencyRate? {
        return dao.getRate(code)
    }
}
