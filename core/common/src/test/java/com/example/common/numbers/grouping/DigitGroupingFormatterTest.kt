package com.example.common.numbers.grouping

import com.example.common.numbers.grouping.DigitGroupingFormatter


import org.junit.Assert.*
import org.junit.Test
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

/**
 * Unit tests for DigitGroupingFormatter (JUnit4).
 * Covers:
 *  - locale() preset: grouping enabled, default locale separator, default group size
 *  - asciiComma() preset: forces ',' regardless of locale
 *  - disabled() preset: grouping off
 *  - persian() preset: U+066C grouping separator
 *  - group size override: groupingSize is applied on DecimalFormat
 *  - formatInteger(): below-compact branch typical usage
 */
class DigitGroupingFormatterTest {

    // --- Helpers ------------------------------------------------------------

    private fun newNumberFormat(locale: Locale): DecimalFormat =
        NumberFormat.getNumberInstance(locale) as DecimalFormat

    private fun groupingSeparatorOf(format: DecimalFormat): Char =
        format.decimalFormatSymbols.groupingSeparator

    // --- locale() preset ----------------------------------------------------

    @Test
    fun `locale preset uses locale's default separator in US`() {
        val f = newNumberFormat(Locale.US)
        val grouping = DigitGroupingFormatter.locale() // enabled, groupSize=3, default separator

        grouping.applyTo(f, Locale.US)

        assertTrue(f.isGroupingUsed)
        assertEquals(3, f.groupingSize)
        assertEquals(',', groupingSeparatorOf(f)) // US locale default is comma
        // Ensure formatting reflects grouping:
        val out = f.format(1_234_567.89)
        assertEquals("1,234,567.89", out)
    }

    @Test
    fun `locale preset uses locale's default separator in Germany`() {
        val de = Locale.GERMANY
        val f = newNumberFormat(de)
        val grouping = DigitGroupingFormatter.locale()

        grouping.applyTo(f, de)

        assertTrue(f.isGroupingUsed)
        assertEquals(3, f.groupingSize)
        assertEquals('.', groupingSeparatorOf(f)) // German locale default is dot
        val out = f.format(1_234_567.89)
        // In de_DE: 1.234.567,89
        assertEquals("1.234.567,89", out)
    }

    // --- asciiComma() preset ------------------------------------------------

    @Test
    fun `asciiComma forces comma even in non-US locales`() {
        val de = Locale.GERMANY
        val f = newNumberFormat(de)
        val grouping = DigitGroupingFormatter.asciiComma() // always ','

        grouping.applyTo(f, de)

        assertTrue(f.isGroupingUsed)
        assertEquals(3, f.groupingSize)
        assertEquals(',', groupingSeparatorOf(f))
        val out = f.format(1_234_567.89)
        // Decimal separator still uses locale (comma for decimal), but grouping is forced to ','
        // So result should look like: 1,234,567,89 for de if pattern keeps decimal separator.
        // However NumberFormat in de uses ',' as decimal sep; grouping we forced to ',',
        // which collides visually; still we assert grouping separator char and that formatting did not crash.
        assertTrue(out.contains(',')) // sanity
    }

    // --- disabled() preset --------------------------------------------------

    @Test
    fun `disabled turns grouping off`() {
        val f = newNumberFormat(Locale.US)
        val grouping = DigitGroupingFormatter.disabled()

        grouping.applyTo(f, Locale.US)

        assertFalse(f.isGroupingUsed)
        // Formatting should have no thousand separators:
        val out = f.format(1_234_567.0)
        assertEquals("1234567", out) // no commas
    }

    // --- persian() preset ---------------------------------------------------

    @Test
    fun `persian uses U+066C as grouping separator`() {
        val f = newNumberFormat(Locale.US)
        val grouping = DigitGroupingFormatter.persian() // grouping sep = U+066C

        grouping.applyTo(f, Locale.US)

        assertTrue(f.isGroupingUsed)
        assertEquals('\u066C', groupingSeparatorOf(f)) // Arabic thousands separator
        val text = grouping.formatInteger(1_234_567L, Locale.US)
        // Expect "1٬234٬567"
        assertEquals("1\u066C234\u066C567", text)
    }

    // --- grouping size override --------------------------------------------

    @Test
    fun `applyTo sets groupingSize on DecimalFormat`() {
        val f = newNumberFormat(Locale.US)
        val grouping = DigitGroupingFormatter.locale(groupSize = 4)

        // Before applyTo, US formatter typically has groupingSize=3
        val before = f.groupingSize
        grouping.applyTo(f, Locale.US)
        val after = f.groupingSize

        assertNotEquals(before, after)
        assertEquals(4, after)

        // We avoid asserting the exact formatted shape for non-3 sizes since DecimalFormat
        // uses primary/secondary grouping internally; here we only ensure the property is applied.
    }

    // --- formatInteger() behavior ------------------------------------------

    @Test
    fun `formatInteger uses grouping policy - US`() {
        val grouping = DigitGroupingFormatter.locale()
        val out = grouping.formatInteger(1_234_567L, Locale.US)
        assertEquals("1,234,567", out)
    }

    @Test
    fun `formatInteger uses grouping policy - Germany`() {
        val grouping = DigitGroupingFormatter.locale()
        val out = grouping.formatInteger(1_234_567L, Locale.GERMANY)
        assertEquals("1.234.567", out)
    }

    @Test
    fun `formatInteger disabled yields plain digits`() {
        val grouping = DigitGroupingFormatter.disabled()
        val out = grouping.formatInteger(1_234_567L, Locale.US)
        assertEquals("1234567", out)
    }
}
