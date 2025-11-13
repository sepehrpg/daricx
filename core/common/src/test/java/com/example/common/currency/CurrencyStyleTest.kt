package com.example.common.currency


import com.example.common.extractor.DomainExtractor
import org.junit.Assert.*
import org.junit.Test


/**
 * Tests for [CurrencyStyle] which uses a lightweight heuristic.
 */
class CurrencyStyleTest {

    // ---------- Basic prefix/suffix placement ----------

    @Test
    fun `prefix without space`() {
        val style = CurrencyStyle(symbol = "$", position = CurrencyStyle.Position.PREFIX, withSpace = false)
        assertEquals("$1,234.56", style.decorate("1,234.56"))
    }

    @Test
    fun `prefix with space`() {
        val style = CurrencyStyle(symbol = "€", position = CurrencyStyle.Position.PREFIX, withSpace = true)
        assertEquals("€ 1.234,56", style.decorate("1.234,56"))
    }

    @Test
    fun `suffix without space`() {
        val style = CurrencyStyle(symbol = "USD", position = CurrencyStyle.Position.SUFFIX, withSpace = false)
        assertEquals("1,234.56USD", style.decorate("1,234.56"))
    }

    @Test
    fun `suffix with space`() {
        val style = CurrencyStyle(symbol = "BTC", position = CurrencyStyle.Position.SUFFIX, withSpace = true)
        assertEquals("1,234.56 BTC", style.decorate("1,234.56"))
    }

    // ---------- Unicode symbols (RTL & crypto signs) ----------

    @Test
    fun `unicode bitcoin prefix`() {
        val style = CurrencyStyle(symbol = "₿", position = CurrencyStyle.Position.PREFIX, withSpace = false)
        assertEquals("₿0.00004560", style.decorate("0.00004560"))
    }

    @Test
    fun `unicode rial suffix with space`() {
        val style = CurrencyStyle(symbol = "﷼", position = CurrencyStyle.Position.SUFFIX, withSpace = true)
        assertEquals("۱۲۳٬۴۵۶ ﷼", style.decorate("۱۲۳٬۴۵۶"))
    }

    @Test
    fun `unicode euro prefix without space`() {
        val style = CurrencyStyle(symbol = "€", position = CurrencyStyle.Position.PREFIX, withSpace = false)
        assertEquals("€1.234,50", style.decorate("1.234,50"))
    }

    // ---------- Multi-char symbols & edge inputs ----------

    @Test
    fun `multi char code as prefix with space`() {
        val style = CurrencyStyle(symbol = "USDT", position = CurrencyStyle.Position.PREFIX, withSpace = true)
        assertEquals("USDT 1,000.00", style.decorate("1,000.00"))
    }

    @Test
    fun `empty symbol prefix`() {
        val style = CurrencyStyle(symbol = "", position = CurrencyStyle.Position.PREFIX, withSpace = false)
        assertEquals("123", style.decorate("123"))
    }

    @Test
    fun `empty symbol suffix`() {
        val style = CurrencyStyle(symbol = "", position = CurrencyStyle.Position.SUFFIX, withSpace = true)
        // withSpace has no visible effect if symbol is empty
        assertEquals("123 ", style.decorate("123"))
    }

    @Test
    fun `empty number string`() {
        val style = CurrencyStyle(symbol = "$", position = CurrencyStyle.Position.PREFIX, withSpace = false)
        assertEquals("$", style.decorate(""))
    }

    @Test
    fun `number string with surrounding spaces is preserved verbatim`() {
        val style = CurrencyStyle(symbol = "$", position = CurrencyStyle.Position.PREFIX, withSpace = true)
        // decorate should not trim or mutate the numeric string
        assertEquals("$   1 234  ", style.decorate("  1 234  "))
    }

    @Test
    fun `long numeric string not altered except decoration`() {
        val style = CurrencyStyle(symbol = "BTC", position = CurrencyStyle.Position.SUFFIX, withSpace = true)
        val num = "123,456,789,012,345.67890123"
        assertEquals("$num BTC", style.decorate(num))
    }

    // ---------- Presets correctness ----------

    @Test
    fun `preset usd`() {
        val s = CurrencyStyle.usd()
        assertEquals("$", s.symbol)
        assertEquals(CurrencyStyle.Position.PREFIX, s.position)
        assertFalse(s.withSpace)
        assertEquals("$1,234.56", s.decorate("1,234.56"))
    }

    @Test
    fun `preset euro`() {
        val s = CurrencyStyle.euro()
        assertEquals("€", s.symbol)
        assertEquals(CurrencyStyle.Position.PREFIX, s.position)
        assertFalse(s.withSpace)
        assertEquals("€1.234,56", s.decorate("1.234,56"))
    }

    @Test
    fun `preset btcPrefix`() {
        val s = CurrencyStyle.btcPrefix()
        assertEquals("₿", s.symbol)
        assertEquals(CurrencyStyle.Position.PREFIX, s.position)
        assertFalse(s.withSpace)
        assertEquals("₿0.01", s.decorate("0.01"))
    }

    @Test
    fun `preset btcSuffixCode`() {
        val s = CurrencyStyle.btcSuffixCode()
        assertEquals("BTC", s.symbol)
        assertEquals(CurrencyStyle.Position.SUFFIX, s.position)
        assertTrue(s.withSpace)
        assertEquals("0.01 BTC", s.decorate("0.01"))
    }

    @Test
    fun `preset rial`() {
        val s = CurrencyStyle.rial()
        assertEquals("﷼", s.symbol)
        assertEquals(CurrencyStyle.Position.SUFFIX, s.position)
        assertTrue(s.withSpace)
        assertEquals("۱٬۲۳۴ ﷼", s.decorate("۱٬۲۳۴"))
    }

    // ---------- Data class semantics (equals, hashCode, copy) ----------

    @Test
    fun `data class equality and hashCode`() {
        val a = CurrencyStyle(symbol = "$", position = CurrencyStyle.Position.PREFIX, withSpace = false)
        val b = CurrencyStyle(symbol = "$", position = CurrencyStyle.Position.PREFIX, withSpace = false)
        val c = CurrencyStyle(symbol = "€", position = CurrencyStyle.Position.PREFIX, withSpace = false)

        assertEquals(a, b)
        assertEquals(a.hashCode(), b.hashCode())
        assertNotEquals(a, c)
    }

    @Test
    fun `copy creates modified instance`() {
        val base = CurrencyStyle(symbol = "$", position = CurrencyStyle.Position.PREFIX, withSpace = false)
        val modified = base.copy(symbol = "USD", position = CurrencyStyle.Position.SUFFIX, withSpace = true)

        assertEquals("123 USD", modified.decorate("123"))
        assertEquals("$123", base.decorate("123")) // ensure base is unchanged (immutability)
    }

    // ---------- Robustness with unusual symbols ----------

    @Test
    fun `symbol with internal space`() {
        val style = CurrencyStyle(symbol = "C$", position = CurrencyStyle.Position.PREFIX, withSpace = true)
        assertEquals("C$ 123.45", style.decorate("123.45"))
    }

    @Test
    fun `very long symbol`() {
        val style = CurrencyStyle(symbol = "MY-SUPER-LONG-CURRENCY-CODE", position = CurrencyStyle.Position.SUFFIX, withSpace = true)
        assertEquals("999 MY-SUPER-LONG-CURRENCY-CODE", style.decorate("999"))
    }
}
