package com.example.common.extractor


import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import java.net.IDN
import java.util.Locale

/**
 * DomainExtractorLite
 *
 * Lightweight version without Guava (NOT PSL-aware).
 * Good for simple TLDs like .com/.org but not reliable for multi-level public suffixes (e.g., .co.uk).
 */
object DomainExtractorLite {

    private val IGNORABLE_SUBDOMAINS = setOf("www", "m", "mobile", "beta", "app")

    fun domain(raw: String): String {
        val url = normalizeUrl(raw)
        val host = url.toHttpUrlOrNull()?.host ?: return raw.trim()
        return heuristic(host)
    }

    private fun normalizeUrl(input: String): String {
        val t = input.trim()
        val hasScheme = t.startsWith("http://", true) || t.startsWith("https://", true)
        return if (hasScheme) t else "https://$t"
    }

    private fun heuristic(host: String): String {
        val unicodeHost = try { IDN.toUnicode(host) } catch (_: Exception) { host }
        val parts = unicodeHost.lowercase(Locale.US).split('.').filter { it.isNotBlank() }
        if (parts.isEmpty()) return unicodeHost

        val cleaned = parts.dropWhile { it in IGNORABLE_SUBDOMAINS }
        if (cleaned.size <= 1) return cleaned.joinToString(".").ifBlank { unicodeHost }

        // Without PSL, we simply take the last two segments.
        return cleaned.takeLast(2).joinToString(".")
    }
}
