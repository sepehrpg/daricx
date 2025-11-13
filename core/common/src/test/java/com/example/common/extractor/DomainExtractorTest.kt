package com.example.common.extractor



import org.junit.Assert.*
import org.junit.Test


/**
 * Tests for [DomainExtractor] which uses a lightweight heuristic.
 */
class DomainExtractorTest {

    @Test fun `simple https url returns correct domain`() {
        assertEquals("mempool.space", DomainExtractor.domain("https://mempool.space/"))
    }

    @Test fun `url with path returns correct domain`() {
        assertEquals("3xpl.com", DomainExtractor.domain("https://3xpl.com/bitcoin"))
    }

    @Test fun `url with www returns correct domain`() {
        assertEquals("bitcoin.org", DomainExtractor.domain("http://www.bitcoin.org"))
    }

    @Test fun `subdomains with country tld returns correct domain`() {
        assertEquals("example.co.uk", DomainExtractor.domain("https://sub.m.example.co.uk/path"))
    }

    @Test fun `url without scheme should normalize and still work`() {
        assertEquals("google.com", DomainExtractor.domain("google.com/search?q=test"))
    }

    @Test fun `url with uppercase and extra spaces handled gracefully`() {
        assertEquals("example.com", DomainExtractor.domain("   HTTP://WWW.EXAMPLE.COM/Page "))
    }


    @Test fun `ip address returns as-is`() {
        assertEquals("192.168.1.1", DomainExtractor.domain("http://192.168.1.1/dashboard"))
    }


    @Test fun `localhost returns as-is`() {
        assertEquals("localhost", DomainExtractor.domain("http://localhost:8080"))
    }

    @Test fun `invalid url returns trimmed raw input`() {
        assertEquals("not a url", DomainExtractor.domain("not a url"))
    }

    @Test fun `handles multi subdomain without crash`() {
        assertEquals("tokenview.io", DomainExtractor.domain("https://btc.tokenview.io/tx/123"))
    }

    @Test fun `handles mobile and beta subdomains`() {
        assertEquals("domain.com", DomainExtractor.domain("https://m.beta.domain.com"))
    }

    @Test fun `handles no host but only path`() {
        assertEquals("/path", DomainExtractor.domain("/path"))
    }

    @Test fun `multiple dots and short middle segment`() {
        val result = DomainExtractor.domain("https://alpha.beta.gamma.app.co.ir")
        assertTrue(result.endsWith("co.ir"))
    }
}

