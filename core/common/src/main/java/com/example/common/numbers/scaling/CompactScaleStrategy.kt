package com.example.common.numbers.scaling



/**
 * CompactScaleStrategy decides how to scale large numbers and which suffix to use.
 * Example steps: K (1e3), M (1e6), B (1e9), T (1e12)
 */
fun interface CompactScaleStrategy {
    fun scale(n: Double): ScaledNumber

    companion object Presets {
        /**
         * International finance style: K, M, B, T
         * Using "B" for Billion (be mindful if you also use "₿" as currency symbol).
         */
        fun international(): CompactScaleStrategy =
            StepsCompactScaleStrategy(
                steps = listOf(
                    ScaleStep(threshold = 1_000.0,   divisor = 1_000.0,        suffix = "K"),
                    ScaleStep(threshold = 1_000_000.0, divisor = 1_000_000.0,    suffix = "M"),
                    ScaleStep(threshold = 1_000_000_000.0, divisor = 1_000_000_000.0, suffix = "B"),
                    ScaleStep(threshold = 1_000_000_000_000.0, divisor = 1_000_000_000_000.0, suffix = "T"),
                )
            )

        /**
         * Alternative to avoid "B" (confusion with ₿). Uses "Bn" for Billion.
         */
        fun internationalNoBConfusion(): CompactScaleStrategy =
            StepsCompactScaleStrategy(
                steps = listOf(
                    ScaleStep(1_000.0, 1_000.0, "K"),
                    ScaleStep(1_000_000.0, 1_000_000.0, "M"),
                    ScaleStep(1_000_000_000.0, 1_000_000_000.0, "Bn"),
                    ScaleStep(1_000_000_000_000.0, 1_000_000_000_000.0, "T"),
                )
            )

        /**
         * Persian short form (example): هزار=K, میلیون=M, میلیارد=B (or "میلیارد")
         * Customize as you wish.
         */
        fun persianShort(): CompactScaleStrategy =
            StepsCompactScaleStrategy(
                steps = listOf(
                    ScaleStep(1_000.0, 1_000.0, "هزار"),
                    ScaleStep(1_000_000.0, 1_000_000.0, "میلیون"),
                    ScaleStep(1_000_000_000.0, 1_000_000_000.0, "میلیارد"),
                    ScaleStep(1_000_000_000_000.0, 1_000_000_000_000.0, "هزار میلیارد"),
                )
            )
    }
}