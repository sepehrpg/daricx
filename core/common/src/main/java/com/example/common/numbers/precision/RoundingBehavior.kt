package com.example.common.numbers.precision


/**
 * RoundingBehavior declares how to round the number AFTER digits have been decided.
 * - RoundedUp:    away from zero (ceil for positive, floor for negative)
 * - RoundedDown:  toward zero (floor for positive, ceil for negative)
 * - HalfEven:     banker's rounding (default financial-safe)
 */
enum class RoundingBehavior {
    RoundedUp,
    RoundedDown,
    HalfEven
}