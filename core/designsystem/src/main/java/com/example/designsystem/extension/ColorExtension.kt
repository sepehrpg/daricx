package com.example.designsystem.extension

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb



fun Color.Companion.hexStringToColor(colorString: String?): Color? {
    return try {
        if (colorString==null) return null
        else Color(android.graphics.Color.parseColor(colorString))
    }
    catch (e:Exception){
        null
    }
}

fun Color.toHexString(): String {
    val red = (this.red * 255).toInt()
    val green = (this.green * 255).toInt()
    val blue = (this.blue * 255).toInt()
    val alpha = (this.alpha * 255).toInt() // مقدار alpha

    return "#${Integer.toHexString(alpha).padStart(2, '0')}" +
            Integer.toHexString(red).padStart(2, '0') +
            Integer.toHexString(green).padStart(2, '0') +
            Integer.toHexString(blue).padStart(2, '0')
}


fun Color.enhanceSaturation(factor: Float = 0.8f): Color {
    val hsv = FloatArray(3)
    android.graphics.Color.colorToHSV(this.toArgb(), hsv)
    hsv[1] = (hsv[1] * factor).coerceAtMost(1.0f) // Increase saturation
    //return Color(android.graphics.Color.HSVToColor(hsv))
    return this
}


fun Color.adjustWarmth(increase: Int, scaling: Float = 0.1f): Color {
    // Convert Color to ARGB int.
    val argb = this.toArgb()
    val a = (argb shr 24) and 0xff
    val r = (argb shr 16) and 0xff
    val g = (argb shr 8) and 0xff
    val b = argb and 0xff
    // Calculate the reductions with scaling.
    val redReduction = ((increase * 63 / 10f) * scaling).toInt()
    val greenReduction = ((increase * 78 / 10f) * scaling).toInt()

    val newR = (r - redReduction).coerceIn(0, 255)
    val newG = (g - greenReduction).coerceIn(0, 255)

    return Color((a shl 24) or (newR shl 16) or (newG shl 8) or b)
}