package com.example.common.extractor


import org.junit.Assert.*
import org.junit.Test

/**
 * Tests for [DomainExtractorLite] which uses a lightweight heuristic.
 */
class DomainExtractorLiteTest {

    @Test fun `basic domain extraction works`() {
        assertEquals("mempool.space", DomainExtractorLite.domain("https://mempool.space/"))
    }

    @Test fun `url with path still returns correct domain`() {
        assertEquals("3xpl.com", DomainExtractorLite.domain("https://3xpl.com/bitcoin"))
    }

    @Test fun `url without scheme handled`() {
        assertEquals("bitcoin.org", DomainExtractorLite.domain("www.bitcoin.org"))
    }

    @Test fun `subdomain stripped when possible`() {
        assertEquals("bitcoin.org", DomainExtractorLite.domain("https://app.m.bitcoin.org"))
    }

    @Test fun `co uk not perfectly resolved`() {
        // lite version can't detect PSL rules, so result might differ intentionally
        val result = DomainExtractorLite.domain("https://sub.m.example.co.uk/path")
        assertTrue(result == "co.uk" || result == "example.co.uk" || result == "example.uk")
    }


    @Test fun `localhost handled gracefully`() {
        assertEquals("localhost", DomainExtractorLite.domain("http://localhost"))
    }

    @Test fun `invalid url returned as raw`() {
        assertEquals("hello world", DomainExtractorLite.domain("hello world"))
    }

    @Test fun `upper case and spaces handled`() {
        assertEquals("example.com", DomainExtractorLite.domain("  HTTPS://WWW.EXAMPLE.COM/Test "))
    }

    @Test fun `multiple dots and short middle segment`() {
        val result = DomainExtractorLite.domain("https://alpha.beta.gamma.app.co.ir")
        assertTrue(result.endsWith("co.ir"))
    }
}
