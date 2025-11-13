package com.example.common.numbers


import com.example.common.currency.CurrencyStyle
import com.example.common.numbers.facade.NumberFormatting
import com.example.common.numbers.facade.NumberFormattingDefaults
import com.example.common.numbers.grouping.DigitGroupingFormatter
import com.example.common.numbers.precision.AutoPricePrecisionPolicy
import com.example.common.numbers.precision.FixedPricePrecisionPolicy
import com.example.common.numbers.precision.RoundingBehavior
import com.example.common.numbers.scaling.CompactScaleStrategy
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Locale

class NumberFormattingTest {

    // ------------------------ pricePretty tests -----------------------------

    @Test
    fun pricePretty_usd_prefix_halfEven_rounding() {
        val fmt = NumberFormatting.defaults(
            scaler = CompactScaleStrategy.international(),
            policy = FixedPricePrecisionPolicy(digits = 2),
            rounding = RoundingBehavior.HalfEven
        )
        val out = fmt.pricePretty(
            price = 12.345,
            currency = CurrencyStyle.usd(),
            trimZeros = false,
            locale = Locale.US
        )
        // Half-even: 12.345 -> 12.34 (2 decimals, last even)
        assertEquals("\$12.34", out)
    }

    @Test
    fun pricePretty_rial_suffix_with_space_and_no_trim() {
        val fmt = NumberFormatting.defaults(
            scaler = CompactScaleStrategy.international(),
            policy = FixedPricePrecisionPolicy(digits = 2),
            rounding = RoundingBehavior.HalfEven
        )
        val out = fmt.pricePretty(
            price = 987.6,
            currency = CurrencyStyle.rial(), // suffix + space
            trimZeros = false,
            locale = Locale.US
        )
        assertEquals("987.60 \uFDFC", out) // \uFDFC is ﷼ (Rial sign)
    }

    @Test
    fun pricePretty_trim_zeros_true() {
        val fmt = NumberFormatting.defaults(
            scaler = CompactScaleStrategy.international(),
            policy = FixedPricePrecisionPolicy(digits = 4),
            rounding = RoundingBehavior.HalfEven
        )
        val out = fmt.pricePretty(
            price = 1.2300,
            currency = CurrencyStyle.usd(),
            trimZeros = true,
            locale = Locale.US
        )
        // digits=4 but trimZeros=true -> "1.23"
        assertEquals("\$1.23", out)
    }

    @Test
    fun pricePretty_roundedUp_and_roundedDown() {
        val baseScaler = CompactScaleStrategy.international()

        val up = NumberFormatting.defaults(
            scaler = baseScaler,
            policy = FixedPricePrecisionPolicy(digits = 2),
            rounding = RoundingBehavior.RoundedUp
        )
        val down = NumberFormatting.defaults(
            scaler = baseScaler,
            policy = FixedPricePrecisionPolicy(digits = 2),
            rounding = RoundingBehavior.RoundedDown
        )

        val upOutPos = up.pricePretty(12.341, CurrencyStyle.usd(), locale = Locale.US)
        val upOutNeg = up.pricePretty(-12.341, CurrencyStyle.usd(), locale = Locale.US)
        val downOut = down.pricePretty(12.349, CurrencyStyle.usd(), locale = Locale.US)

        assertEquals("\$12.35", upOutPos)   // away from zero
        assertEquals("\$-12.35", upOutNeg)  // away from zero (negative)
        assertEquals("\$12.34", downOut)    // toward zero
    }

    // ------------------------ compactNumber tests --------------------------

    @Test
    fun compactNumber_k_m_b_t_and_currency_prefix() {
        val fmt = NumberFormatting.defaults(
            scaler = CompactScaleStrategy.international(),
            policy = FixedPricePrecisionPolicy(digits = 2)
        )

        val k = fmt.compactNumber(1_532.0, locale = Locale.US, fractionDigits = 2, currency = CurrencyStyle.usd())
        val m = fmt.compactNumber(2_450_000.0, locale = Locale.US, fractionDigits = 2, currency = CurrencyStyle.usd())
        val b = fmt.compactNumber(7_200_000_000.0, locale = Locale.US, fractionDigits = 2, currency = CurrencyStyle.usd())
        val t = fmt.compactNumber(3_450_000_000_000.0, locale = Locale.US, fractionDigits = 2, currency = CurrencyStyle.usd())

        assertEquals("\$1.53K", k)
        assertEquals("\$2.45M", m)
        assertEquals("\$7.20B", b)
        assertEquals("\$3.45T", t)
    }

    @Test
    fun compactNumber_below_threshold_returns_integer_legacy_behavior() {
        val fmt = NumberFormatting.defaults(
            scaler = CompactScaleStrategy.international(),
            policy = FixedPricePrecisionPolicy(digits = 2)
        )
        val out950 = fmt.compactNumber(950.0, locale = Locale.US, fractionDigits = 2, currency = CurrencyStyle.usd())
        val out999_9 = fmt.compactNumber(999.9, locale = Locale.US, fractionDigits = 2, currency = CurrencyStyle.usd())
        // Below first threshold => integer formatting with grouping
        assertEquals("\$950", out950)
        assertEquals("\$999", out999_9)
    }

    @Test
    fun compactNumber_null_returns_dash() {
        val fmt = NumberFormattingDefaults.default
        val out = fmt.compactNumber(null, locale = Locale.US, fractionDigits = 2, currency = CurrencyStyle.usd())
        assertEquals("—", out)
    }

    @Test
    fun compactNumber_with_space_separator_between_value_and_suffix() {
        val fmt = NumberFormatting.defaults(
            scaler = CompactScaleStrategy.international(),
            policy = FixedPricePrecisionPolicy(digits = 2),
            rounding = RoundingBehavior.HalfEven,
            suffixSeparator = " "
        )
        val out = fmt.compactNumber(1_530.0, locale = Locale.US, fractionDigits = 2, currency = CurrencyStyle.usd())
        assertEquals("\$1.53 K", out)
    }

    @Test
    fun compactNumber_internationalNoBConfusion_uses_Bn() {
        val fmt = NumberFormatting.defaults(
            scaler = CompactScaleStrategy.internationalNoBConfusion(),
            policy = FixedPricePrecisionPolicy(digits = 2)
        )
        val m = fmt.compactNumber(999_999_999.0, locale = Locale.US, fractionDigits = 3, currency = CurrencyStyle.usd())
        val bn1 = fmt.compactNumber(1_000_000_000.0, locale = Locale.US, fractionDigits = 2, currency = CurrencyStyle.usd())
        assertEquals("\$1000.000M", m) // still under Bn step => shown as millions
        assertEquals("\$1.00Bn", bn1)  // at 1e9 => Bn
    }

    // ------------------------ defaults wiring ------------------------------

    @Test
    fun numberFormattingDefaults_exist_and_work() {
        val fmt = NumberFormattingDefaults.default
        val text = fmt.pricePretty(12.345, CurrencyStyle.usd(), trimZeros = false, locale = Locale.US)
        // Using Auto policy with HalfEven; 12.345 -> 2 digits -> 12.34
        assertEquals("\$12.34", text)
    }


    @Test
    fun grouping_number() {
        /*val fmt = NumberFormatting.defaults(
            scaler = CompactScaleStrategy.international(),
            policy = FixedPricePrecisionPolicy(0),
            rounding = RoundingBehavior.HalfEven,
            grouping = DigitGroupingFormatter.locale(),
            suffixSeparator = "" // set to " " to render like "1.53 M"
        )*/
        val fmt = NumberFormattingDefaults.default
        val text = fmt.pricePretty(
            123456.toDouble(),
            CurrencyStyle.empty(),
            trimZeros = true,
            locale = Locale.US
        )
        // Using Auto policy with HalfEven; 12.345 -> 2 digits -> 12.34
        assertEquals("123,456", text)

        val text2 = fmt.pricePretty(
            123456.toDouble(),
            CurrencyStyle(symbol = CurrencyStyle.usd().symbol, position = CurrencyStyle.Position.SUFFIX, withSpace = false),
            trimZeros = false,
            locale = Locale.US
        )
        assertEquals("123,456$", text2)

        val text3 = fmt.compactNumber(
            1234561231233712.toDouble(),
        )
        assertEquals("1234.56T", text3)

    }
}
