package com.bm.currencyapplication.convertcurrency.presentation


import com.bm.currencyapplication.convertcurrency.domain.AvailableRatesUsecase
import com.bm.currencyapplication.convertcurrency.domain.ConvertCurrencyUsecase
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule


class ConvertCurrencyViewModelTest {

    @Mock
    lateinit var convertCurrencyUsecase: ConvertCurrencyUsecase

    @Mock
    lateinit var availableRatesUsecase: AvailableRatesUsecase

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Test
    fun `Given the vm is ready, then it is initialized`() {

        val vm = ConvertCurrencyViewModel(convertCurrencyUsecase, availableRatesUsecase)

        assertTrue(vm.items.value == ConvertCurrencyState.Initializing)
    }

}