package com.example.common.extractor


import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import com.google.common.net.InternetDomainName
import java.net.IDN
import java.util.Locale
import java.util.regex.Pattern

/**
 * DomainExtractor
 *
 * Single-responsibility utility to extract the registrable domain (a.k.a. top private domain)
 * from any given URL string. Examples:
 *
 *  - https://mempool.space/           -> mempool.space
 *  - https://3xpl.com/bitcoin         -> 3xpl.com
 *  - http://www.bitcoin.org           -> bitcoin.org
 *  - https://sub.m.example.co.uk/x    -> example.co.uk
 *
 * Design:
 * 1) Normalize scheme (prepend https:// if missing).
 * 2) Parse safely via OkHttp to obtain the host.
 * 3) Resolve registrable domain with Guava + Public Suffix List.
 * 4) Unicode-friendly output for IDN domains.
 * 5) Graceful fallback when PSL resolution fails.
 * Now improved for:
 *  - IPv4 & IPv6 detection
 *  - Path-only inputs
 *  - Unicode (IDN) normalization
 */
object DomainExtractor {

    private val IGNORABLE_SUBDOMAINS = setOf("www", "m", "mobile", "beta", "app")
    private val IPV4_PATTERN = Pattern.compile(
        "^((25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.|$)){4}$"
    )
    private val IPV6_PATTERN = Pattern.compile(
        "^(\\[[0-9a-fA-F:]+]|[0-9a-fA-F:]+)$"
    )

    /**
     * Returns the registrable domain from the given raw URL string.
     */
    fun domain(raw: String): String {
        val trimmed = raw.trim()

        // Case 1: path-only string (no domain)
        if (trimmed.startsWith("/")) return trimmed

        val url = normalizeUrl(trimmed)
        val host = url.toHttpUrlOrNull()?.host ?: return trimmed

        // Case 2: handle IP addresses directly
        if (isIpAddress(host)) return host

        // Case 3: PSL-based domain extraction
        pslDomain(host)?.let { return it }

        // Case 4: fallback heuristic
        return fallbackDomain(host)
    }

    /** Add https:// if missing. */
    private fun normalizeUrl(input: String): String {
        val hasScheme = input.startsWith("http://", true) || input.startsWith("https://", true)
        return if (hasScheme) input else "https://$input"
    }

    /** Detect IPv4 or IPv6 hostnames. */
    private fun isIpAddress(host: String): Boolean {
        return IPV4_PATTERN.matcher(host).matches() || IPV6_PATTERN.matcher(host).matches()
    }

    /** Resolve top private domain via Guava + PSL. */
    private fun pslDomain(host: String): String? = try {
        val ascii = IDN.toASCII(host, IDN.ALLOW_UNASSIGNED)
        val registrableAscii = InternetDomainName.from(ascii).topPrivateDomain().toString()
        IDN.toUnicode(registrableAscii)
    } catch (_: Exception) {
        null
    }

    /** Simple fallback if PSL fails. */
    private fun fallbackDomain(host: String): String {
        val unicodeHost = try { IDN.toUnicode(host) } catch (_: Exception) { host }
        val parts = unicodeHost.lowercase(Locale.US).split('.').filter { it.isNotBlank() }
        if (parts.isEmpty()) return unicodeHost

        val cleaned = parts.dropWhile { it in IGNORABLE_SUBDOMAINS }
        if (cleaned.size <= 1) return cleaned.joinToString(".").ifBlank { unicodeHost }

        return if (cleaned.size >= 3 && cleaned[cleaned.lastIndex - 1].length <= 3) {
            cleaned.takeLast(3).joinToString(".")
        } else {
            cleaned.takeLast(2).joinToString(".")
        }
    }
}

