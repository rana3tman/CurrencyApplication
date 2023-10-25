package com.bm.currencyapplication.convertcurrency.domain

import com.bm.currencyapplication.utils.DataState
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ConvertCurrencyUsecase @Inject constructor(private var availableRatesRepository: AvailableRatesRepository) {
    suspend fun invoke(date: String, base: String, symbols: String) = flow {
        emit(DataState.Initializing)
        val result = availableRatesRepository.getAllCurrencyRate(date, base, symbols)

        emit(
            if (result.success)
                (DataState.Success(result))
            else (DataState.Error(result.error.type))
        )

    }
}