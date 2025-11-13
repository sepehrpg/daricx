package com.example.common.numbers.precision


import org.junit.Assert.*
import org.junit.Test
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.ln

/**
 * Test suite for [AutoPricePrecisionPolicy].
 *
 * Coverage:
 *  - fractionDigits buckets and exact thresholds (>=1, >=0.1, >=0.01, >=0.001, tiny (0,0.001))
 *  - zero handling
 *  - negatives with MirrorPositive vs Fixed(digits)
 *  - tinyDigitsRange clamping
 *  - applyRounding for HalfEven, RoundedUp, RoundedDown (positive/negative)
 *  - tie cases (banker's rounding) and tiny values rounding
 */
class AutoPricePrecisionPolicyTest {

    // ---------------- Helpers ----------------

    /**
     * Computes the expected tiny digits by the same mathematical definition:
     * digits = clip[range]( floor(2 - log10(value)) )
     */
    private fun expectedTinyDigits(value: Double, range: IntRange = 6..10): Int {
        val base10 = ln(value) / ln(10.0)
        return (2 - base10).toInt().coerceIn(range.first, range.last)
    }

    /** Mirrors BigDecimal rounding used by the implementation. */
    private fun bdRound(v: Double, digits: Int, mode: RoundingMode): Double {
        return BigDecimal(v).setScale(digits, mode).toDouble()
    }

    // ---------------- fractionDigits: main buckets ----------------

    @Test
    fun `digits for values  1 are 2`() { //digits for values >= 1 are 2
        val p = AutoPricePrecisionPolicy()
        assertEquals(2, p.fractionDigits(1.0))
        assertEquals(2, p.fractionDigits(1.2345))
        assertEquals(2, p.fractionDigits(1234.0))
    }

    @Test
    fun `digits for 0_1 to 1 are 3`() {
        val p = AutoPricePrecisionPolicy()
        assertEquals(3, p.fractionDigits(0.1))
        assertEquals(3, p.fractionDigits(0.345))
        assertEquals(3, p.fractionDigits(0.9999))
    }

    @Test
    fun `digits for 0_01 to 0_1 are 4`() {
        val p = AutoPricePrecisionPolicy()
        assertEquals(4, p.fractionDigits(0.01))
        assertEquals(4, p.fractionDigits(0.0456))
        assertEquals(4, p.fractionDigits(0.09999))
    }

    @Test
    fun `digits for 0_001 to 0_01 are 5`() {
        val p = AutoPricePrecisionPolicy()
        assertEquals(5, p.fractionDigits(0.001))
        assertEquals(5, p.fractionDigits(0.00321))
        assertEquals(5, p.fractionDigits(0.00999))
    }

    @Test
    fun `digits for tiny values in (0, 0_001) are dynamic and clamped to 6-10`() {
        val p = AutoPricePrecisionPolicy()
        val v1 = 0.0009
        val v2 = 0.000045
        val v3 = 0.00000091
        val v4 = 0.0000000045

        val d1 = p.fractionDigits(v1)
        val d2 = p.fractionDigits(v2)
        val d3 = p.fractionDigits(v3)
        val d4 = p.fractionDigits(v4)

        assertEquals(expectedTinyDigits(v1), d1)
        assertEquals(expectedTinyDigits(v2), d2)
        assertEquals(expectedTinyDigits(v3), d3)
        assertEquals(expectedTinyDigits(v4), d4)

        for (d in listOf(d1, d2, d3, d4)) {
            assertTrue(d in 6..10)
        }
    }

    @Test
    fun `digits for zero is 0`() {
        val p = AutoPricePrecisionPolicy()
        assertEquals(0, p.fractionDigits(0.0))
    }

    // ---------------- fractionDigits: negatives ----------------

    @Test
    fun `negatives with MirrorPositive mirror positives`() {
        val p = AutoPricePrecisionPolicy(negativeMode = NegativePrecisionMode.MirrorPositive)
        assertEquals(2, p.fractionDigits(-1234.5))   // mirrors >=1
        assertEquals(4, p.fractionDigits(-0.0456))   // mirrors [0.01..0.1)
        val tinyNeg = -0.00000091
        assertEquals(expectedTinyDigits(0.00000091), p.fractionDigits(tinyNeg))
    }

    @Test
    fun `negatives with Fixed digits always return fixed`() {
        val p = AutoPricePrecisionPolicy(negativeMode = NegativePrecisionMode.Fixed(6))
        assertEquals(6, p.fractionDigits(-1234.5))
        assertEquals(6, p.fractionDigits(-0.0456))
        assertEquals(6, p.fractionDigits(-0.00000091))
    }

    // ---------------- tinyDigitsRange clamp customization ----------------

    @Test
    fun `tinyDigitsRange custom single value clamp is respected`() {
        val p = AutoPricePrecisionPolicy(tinyDigitsRange = 8..8)
        assertEquals(8, p.fractionDigits(0.0009))
        assertEquals(8, p.fractionDigits(0.00000091))
        assertEquals(2, p.fractionDigits(1.23)) // non-tiny still follows normal rules
    }

    @Test
    fun `extremely small values clamp to 10 by default`() {
        val p = AutoPricePrecisionPolicy()
        assertEquals(10, p.fractionDigits(1e-12))
        assertEquals(10, p.fractionDigits(1e-18))
    }

    // ---------------- applyRounding: HalfEven, RoundedUp, RoundedDown ----------------

    @Test
    fun `applyRounding HalfEven for positive non tie`() {
        val p = AutoPricePrecisionPolicy(rounding = RoundingBehavior.HalfEven)
        // digits=2 (>=1)
        val v1 = 1.2344
        val v2 = 1.2346
        assertEquals(bdRound(v1, 2, RoundingMode.HALF_EVEN), p.applyRounding(v1), 1e-12)
        assertEquals(bdRound(v2, 2, RoundingMode.HALF_EVEN), p.applyRounding(v2), 1e-12)
    }

    @Test
    fun `applyRounding HalfEven tie cases (banker's rounding) positive`() {
        val p = AutoPricePrecisionPolicy(rounding = RoundingBehavior.HalfEven)
        // digits=2 (>=1)
        val vOdd  = 1.235   // 1.23[5] -> goes to 1.24 (even 4)
        val vEven = 1.245   // 1.24[5] -> stays 1.24 (even 4)
        assertEquals(bdRound(vOdd,  2, RoundingMode.HALF_EVEN), p.applyRounding(vOdd),  1e-12)
        assertEquals(bdRound(vEven, 2, RoundingMode.HALF_EVEN), p.applyRounding(vEven), 1e-12)
    }

    @Test
    fun `applyRounding RoundedUp for positive increases magnitude`() {
        val p = AutoPricePrecisionPolicy(rounding = RoundingBehavior.RoundedUp)
        val v = 1.2301 // digits=2 -> 1.24 (away from zero)
        assertEquals(bdRound(v, 2, RoundingMode.UP), p.applyRounding(v), 1e-12)
    }

    @Test
    fun `applyRounding RoundedDown for positive moves toward zero`() {
        val p = AutoPricePrecisionPolicy(rounding = RoundingBehavior.RoundedDown)
        val v = 1.239 // digits=2 -> 1.23 (toward zero)
        assertEquals(bdRound(v, 2, RoundingMode.DOWN), p.applyRounding(v), 1e-12)
    }

    @Test
    fun `applyRounding HalfEven for negative non tie`() {
        val p = AutoPricePrecisionPolicy(rounding = RoundingBehavior.HalfEven)
        val v = -1.2346 // digits=2
        assertEquals(bdRound(v, 2, RoundingMode.HALF_EVEN), p.applyRounding(v), 1e-12)
    }

    @Test
    fun `applyRounding RoundedUp for negative is away from zero (more negative)`() {
        val p = AutoPricePrecisionPolicy(rounding = RoundingBehavior.RoundedUp)
        val v = -1.2301 // digits=2 -> -1.24
        assertEquals(bdRound(v, 2, RoundingMode.UP), p.applyRounding(v), 1e-12)
    }

    @Test
    fun `applyRounding RoundedDown for negative is toward zero (less negative)`() {
        val p = AutoPricePrecisionPolicy(rounding = RoundingBehavior.RoundedDown)
        val v = -1.239 // digits=2 -> -1.23
        assertEquals(bdRound(v, 2, RoundingMode.DOWN), p.applyRounding(v), 1e-12)
    }

    @Test
    fun `applyRounding respects dynamic tiny digits for tiny values`() {
        val p = AutoPricePrecisionPolicy(rounding = RoundingBehavior.HalfEven)
        val v = 0.0000456789 // tiny -> digits in [6..10], typically ~8
        val digits = p.fractionDigits(v)
        assertTrue(digits in 6..10)
        assertEquals(bdRound(v, digits, RoundingMode.HALF_EVEN), p.applyRounding(v), 1e-12)
    }

    @Test
    fun `applyRounding for zero uses digits==0 branch for all behaviors`() {
        val z = 0.0
        assertEquals(0.0, AutoPricePrecisionPolicy(rounding = RoundingBehavior.HalfEven).applyRounding(z), 0.0)
        assertEquals(0.0, AutoPricePrecisionPolicy(rounding = RoundingBehavior.RoundedUp).applyRounding(z), 0.0)
        assertEquals(0.0, AutoPricePrecisionPolicy(rounding = RoundingBehavior.RoundedDown).applyRounding(z), 0.0)
    }

    // ---------------- threshold edge assertions ----------------

    @Test
    fun `threshold edges exact boundaries`() {
        val p = AutoPricePrecisionPolicy()
        assertEquals(2, p.fractionDigits(1.0))
        assertEquals(3, p.fractionDigits(0.1))
        assertEquals(4, p.fractionDigits(0.01))
        assertEquals(5, p.fractionDigits(0.001))
    }

    @Test
    fun `threshold edges just below boundaries`() {
        val p = AutoPricePrecisionPolicy()
        assertEquals(3, p.fractionDigits(0.999999))
        assertEquals(4, p.fractionDigits(0.099999))
        assertEquals(5, p.fractionDigits(0.009999))
        val tinyJustBelow = 0.000999
        val d = p.fractionDigits(tinyJustBelow)
        assertTrue(d in 6..10)
    }

    // ---------------- tie case near 1_000_000 boundary but with bucket rule ----------------

    @Test
    fun `half even rounding can cross integer boundary when bucket digits=3`() {
        val p = AutoPricePrecisionPolicy(rounding = RoundingBehavior.HalfEven)
        // In (0.1..1) bucket => 3 digits. 0.99995 with 3 digits => 1.000 (banker's rounding)
        val v = 0.99995
        val digits = p.fractionDigits(v)
        assertEquals(3, digits)
        assertEquals(bdRound(v, digits, RoundingMode.HALF_EVEN), p.applyRounding(v), 1e-12)
        assertEquals(1.0, p.applyRounding(v), 1e-12)
    }
}



