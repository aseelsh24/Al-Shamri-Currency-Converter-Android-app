package com.alshamri.currencyconverter.domain.usecase

import java.math.BigDecimal
import java.math.RoundingMode

class ConvertCurrencyUseCase {

    /**
     * Executes the currency conversion logic.
     *
     * @param amount The amount to convert.
     * @param sourceRate The exchange rate of the source currency relative to USD.
     * @param targetRate The exchange rate of the target currency relative to USD.
     * @return The converted amount, rounded to 4 decimal places. Returns null if conversion is not possible.
     */
    operator fun invoke(
        amount: Double,
        sourceRate: Double,
        targetRate: Double
    ): Double? {
        if (sourceRate <= 0) return null
        if (amount == 0.0) return 0.0

        // Use BigDecimal for precision
        val amountBd = BigDecimal.valueOf(amount)
        val sourceRateBd = BigDecimal.valueOf(sourceRate)
        val targetRateBd = BigDecimal.valueOf(targetRate)

        val amountInUsd = amountBd.divide(sourceRateBd, 10, RoundingMode.HALF_UP)
        val convertedAmount = amountInUsd.multiply(targetRateBd)

        return convertedAmount.setScale(4, RoundingMode.HALF_UP).toDouble()
    }
}
