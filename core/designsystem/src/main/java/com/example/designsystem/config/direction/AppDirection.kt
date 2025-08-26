package com.example.designsystem.config.direction

import android.app.Activity
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.ProvidedValue
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.core.os.LocaleListCompat


enum class LayoutDirections {
    RTL,LTR
}


object AppDirection {
    fun appLayoutDirectionProvider(dir:LayoutDirections = LayoutDirections.LTR): ProvidedValue<*> {
        return LocalLayoutDirection provides
                if (dir==LayoutDirections.LTR) androidx.compose.ui.unit.LayoutDirection.Ltr else
                    androidx.compose.ui.unit.LayoutDirection.Rtl
    }
}