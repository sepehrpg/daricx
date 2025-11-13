package com.example.common.numbers.scaling


import org.junit.Assert.*
import org.junit.Test

/**
 * Unit tests for Compact scaling classes using JUnit4.
 * Covers: thresholds, exact boundaries, below-threshold, negatives, custom steps,
 * presets (international / no-B-confusion / persian), NaN/Infinity, and constructor validation.
 */
class StepsCompactScaleStrategyTest {

    // --- Helpers -------------------------------------------------------------

    private fun assertScaled(
        strategy: CompactScaleStrategy,
        input: Double,
        expectedValue: Double,
        expectedSuffix: String?,
        delta: Double = 1e-9
    ) {
        val out = strategy.scale(input)
        assertEquals("value mismatch for $input", expectedValue, out.value, delta)
        assertEquals("suffix mismatch for $input", expectedSuffix, out.suffix)
    }

    // --- Core behavior with a simple custom strategy ------------------------

    @Test
    fun `returns null suffix when below first threshold`() {
        val s = StepsCompactScaleStrategy(
            listOf(ScaleStep(1_000.0, 1_000.0, "K"))
        )
        assertScaled(s, 999.0, 999.0, null)
        assertScaled(s, -999.0, -999.0, null)
    }

    @Test
    fun `picks exact first step at threshold`() {
        val s = StepsCompactScaleStrategy(
            listOf(ScaleStep(1_000.0, 1_000.0, "K"))
        )
        assertScaled(s, 1_000.0, 1.0, "K")
        assertScaled(s, -1_000.0, -1.0, "K")
    }

    @Test
    fun `picks the highest eligible step (multi-step)`() {
        val s = StepsCompactScaleStrategy(
            listOf(
                ScaleStep(1_000.0, 1_000.0, "K"),
                ScaleStep(1_000_000.0, 1_000_000.0, "M"),
            )
        )
        assertScaled(s, 1_500.0, 1.5, "K")
        assertScaled(s, 2_450_000.0, 2.45, "M")
        assertScaled(s, -3_600_000.0, -3.6, "M")
    }

    @Test
    fun `supports custom divisor different from threshold`() {
        // Step activates from 1500 up, but divides by 1000 to show "K"
        val s = StepsCompactScaleStrategy(
            listOf(ScaleStep(1_500.0, 1_000.0, "K"))
        )
        assertScaled(s, 1_499.99, 1_499.99, null)
        assertScaled(s, 1_500.0, 1.5, "K")
        assertScaled(s, 12_345.0, 12.345, "K")
    }

    // --- Presets: international ---------------------------------------------

    @Test
    fun `international preset - below threshold`() {
        val intl = CompactScaleStrategy.international()
        assertScaled(intl, 950.0, 950.0, null)
    }

    @Test
    fun `international preset - K M B T boundaries`() {
        val intl = CompactScaleStrategy.international()
        // K
        assertScaled(intl, 1_000.0, 1.0, "K")
        assertScaled(intl, 12_345.0, 12.345, "K")
        // M
        assertScaled(intl, 1_000_000.0, 1.0, "M")
        assertScaled(intl, 2_450_000.0, 2.45, "M")
        // B
        assertScaled(intl, 1_000_000_000.0, 1.0, "B")
        assertScaled(intl, 7_200_000_000.0, 7.2, "B")
        // T
        assertScaled(intl, 1_000_000_000_000.0, 1.0, "T")
        assertScaled(intl, 3_450_000_000_000.0, 3.45, "T")
    }

    @Test
    fun `international preset - negatives mirror behavior`() {
        val intl = CompactScaleStrategy.international()
        assertScaled(intl, -1_500.0, -1.5, "K")
        assertScaled(intl, -1_000_000.0, -1.0, "M")
        assertScaled(intl, -1_000_000_000.0, -1.0, "B")
    }

    // --- Presets: internationalNoBConfusion --------------------------------

    @Test
    fun `internationalNoBConfusion uses Bn at 1e9`() {
        val intlBn = CompactScaleStrategy.internationalNoBConfusion()
        assertScaled(intlBn, 999_999_999.0, 999.999999, "M")
        assertScaled(intlBn, 1_000_000_000.0, 1.0, "Bn")
        assertScaled(intlBn, 1_200_000_000.0, 1.2, "Bn")
    }

    // --- Presets: persianShort ---------------------------------------------

    @Test
    fun `persianShort preset - correct suffixes`() {
        val fa = CompactScaleStrategy.persianShort()
        assertScaled(fa, 1_000.0, 1.0, "هزار")
        assertScaled(fa, 1_000_000.0, 1.0, "میلیون")
        assertScaled(fa, 1_234_000_000.0, 1.234, "میلیارد")
        assertScaled(fa, 1_000_000_000_000.0, 1.0, "هزار میلیارد")
    }

    // --- Special numeric cases ---------------------------------------------

    @Test
    fun `NaN returns NaN with null suffix`() {
        val intl = CompactScaleStrategy.international()
        val out = intl.scale(Double.NaN)
        assertTrue(out.value.isNaN())
        assertNull(out.suffix)
    }

    @Test
    fun `positive and negative infinity choose highest step`() {
        val intl = CompactScaleStrategy.international()
        val pos = intl.scale(Double.POSITIVE_INFINITY)
        val neg = intl.scale(Double.NEGATIVE_INFINITY)
        assertTrue(pos.value.isInfinite())
        assertTrue(neg.value.isInfinite())
        // Highest step in preset is T
        assertEquals("T", pos.suffix)
        assertEquals("T", neg.suffix)
    }

    // --- Constructor validation --------------------------------------------

    @Test(expected = IllegalArgumentException::class)
    fun `constructor throws when steps empty`() {
        StepsCompactScaleStrategy(emptyList())
    }

    @Test(expected = IllegalArgumentException::class)
    fun `constructor throws when steps not sorted ascending`() {
        StepsCompactScaleStrategy(
            listOf(
                ScaleStep(1_000_000.0, 1_000_000.0, "M"),
                ScaleStep(1_000.0, 1_000.0, "K")
            )
        )
    }
}
