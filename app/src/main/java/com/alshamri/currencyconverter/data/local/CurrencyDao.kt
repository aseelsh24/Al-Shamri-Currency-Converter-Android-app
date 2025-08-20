package com.alshamri.currencyconverter.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(rates: List<CurrencyRate>)

    @Query("SELECT * FROM currency_rates")
    fun getAllRates(): Flow<List<CurrencyRate>>

    @Query("SELECT * FROM currency_rates WHERE currencyCode = :code")
    suspend fun getRate(code: String): CurrencyRate?

    @Query("UPDATE currency_rates SET rate = :newRate WHERE currencyCode = :code")
    suspend fun updateRate(code: String, newRate: Double)

}
