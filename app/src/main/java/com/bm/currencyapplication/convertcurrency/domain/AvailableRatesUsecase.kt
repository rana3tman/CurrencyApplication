package com.bm.currencyapplication.convertcurrency.domain

import com.bm.currencyapplication.utils.DataState
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AvailableRatesUsecase @Inject constructor(private var availableRatesRepository: AvailableRatesRepository) {
    suspend fun invoke() = flow {
        emit(DataState.Initializing)
        val result = availableRatesRepository.getAllAvailableRates()

        emit(
            if (result.success)
                (DataState.Success(result))
            else (DataState.Error(result.error.type))
        )

    }
}