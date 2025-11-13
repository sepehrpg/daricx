package com.example.common.numbers.precision

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.math.BigDecimal


/**
 * Unit tests for [FixedPricePrecisionPolicy].
 */
class FixedPricePrecisionPolicyTest {

    // region fractionDigits

    @Test
    fun fractionDigits_returnsConfiguredDigits_forPositiveValues() {
        val policy = FixedPricePrecisionPolicy(digits = 2)
        assertEquals(2, policy.fractionDigits(123.456))
        assertEquals(2, policy.fractionDigits(0.1))
    }

    @Test
    fun fractionDigits_mirrorsPositiveDigits_whenNegativeMirrorMode() {
        val policy = FixedPricePrecisionPolicy(
            digits = 3,
            negativeMode = NegativePrecisionMode.MirrorPositive
        )
        assertEquals(3, policy.fractionDigits(-45.67))
    }

    @Test
    fun fractionDigits_usesFixedDigits_forNegativeFixedMode() {
        val policy = FixedPricePrecisionPolicy(
            digits = 2,
            negativeMode = NegativePrecisionMode.Fixed(4)
        )
        assertEquals(4, policy.fractionDigits(-12.34))
        assertEquals(2, policy.fractionDigits(12.34))
    }

    // endregion

    // region applyRounding - HalfEven

    @Test
    fun applyRounding_usesHalfEvenByDefault() {
        val policy = FixedPricePrecisionPolicy(digits = 2)
        assertEquals(12.34, policy.applyRounding(12.344), 0.0001)
        assertEquals(12.34, policy.applyRounding(12.345), 0.0001)
        assertEquals(0.12, policy.applyRounding(0.1249), 0.0001)
    }

    // endregion

    // region applyRounding - RoundedUp

    @Test
    fun applyRounding_roundsAwayFromZero_whenRoundedUp() {
        val policy = FixedPricePrecisionPolicy(
            digits = 2,
            rounding = RoundingBehavior.RoundedUp
        )

        assertEquals(12.35, policy.applyRounding(12.341), 0.0001)
        assertEquals(-12.35, policy.applyRounding(-12.341), 0.0001)
        assertEquals(0.13, policy.applyRounding(0.121), 0.0001)
    }

    // endregion

    // region applyRounding - RoundedDown

    @Test
    fun applyRounding_roundsTowardZero_whenRoundedDown() {
        val policy = FixedPricePrecisionPolicy(
            digits = 2,
            rounding = RoundingBehavior.RoundedDown
        )

        assertEquals(12.34, policy.applyRounding(12.349), 0.0001)
        assertEquals(-12.34, policy.applyRounding(-12.349), 0.0001)
        assertEquals(0.12, policy.applyRounding(0.129), 0.0001)
    }

    // endregion

    // region zero and edge cases

    @Test
    fun applyRounding_handlesZeroCorrectly() {
        val policy = FixedPricePrecisionPolicy(digits = 3)
        assertEquals(0.000, policy.applyRounding(0.0), 0.0)
    }

    @Test
    fun applyRounding_handlesVerySmallNumbers() {
        val policy = FixedPricePrecisionPolicy(digits = 6)
        assertEquals(0.000001, policy.applyRounding(0.0000012), 0.0)
    }

    @Test
    fun applyRounding_handlesLargeNumbers() {
        val policy = FixedPricePrecisionPolicy(digits = 2)
        assertEquals(123456789.12, policy.applyRounding(123456789.1234), 0.0001)
    }

    // endregion

    // region invalid argument

    @Test(expected = IllegalArgumentException::class)
    fun throwsException_whenDigitsNegative() {
        FixedPricePrecisionPolicy(-1)
    }

    // endregion

    // region consistency check

    @Test
    fun applyRounding_resultHasCorrectNumberOfFractionDigits() {
        val policy = FixedPricePrecisionPolicy(digits = 3)
        val result = policy.applyRounding(123.456789)
        val decimalDigits = BigDecimal.valueOf(result)
            .stripTrailingZeros()
            .scale()
        assertTrue(decimalDigits <= 3)
    }

    // endregion
}
