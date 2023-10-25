package com.bm.currencyapplication.utils

import org.junit.Assert.*

import org.junit.Test

class CalculatorTest {

    @Test
    fun calculateConversionMultiply() {
        assertEquals("Result is correct", 15.0, Calculator.calculateConversionMultiply(3.0,5.0), 0.0)
    }

    @Test
    fun calculateConversionDivision() {
        assertEquals("Result is correct", 3.0, Calculator.calculateConversionDivision(15.0,5.0), 0.0)

    }
}