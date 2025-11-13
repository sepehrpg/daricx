package com.example.common.numbers.scaling

import kotlin.math.abs


/**
 * A compact scaling strategy backed by an ordered list of [ScaleStep]s.
 * First step whose threshold <= |n| applies.
 */
class StepsCompactScaleStrategy(
    private val steps: List<ScaleStep>
) : CompactScaleStrategy {

    init {
        require(steps.isNotEmpty()) { "steps must not be empty" }
        // Optional: ensure ascending thresholds
        require(steps == steps.sortedBy { it.threshold }) { "steps must be sorted ascending by threshold" }
    }

    override fun scale(n: Double): ScaledNumber {
        val a = abs(n)
        // Find the highest threshold that is <= a
        val step = steps.lastOrNull { a >= it.threshold } ?: return ScaledNumber(value = n, suffix = null)
        val scaled = n / step.divisor
        return ScaledNumber(value = scaled, suffix = step.suffix)
    }
}