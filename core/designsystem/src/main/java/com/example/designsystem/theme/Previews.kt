package com.example.designsystem.theme

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp


/*@Preview(name = "phone", device = "spec:shape=Normal,width=360,height=640,unit=dp,dpi=480")
@Preview(name = "landscape", device = "spec:shape=Normal,width=640,height=360,unit=dp,dpi=480")
@Preview(name = "foldable", device = "spec:shape=Normal,width=673,height=841,unit=dp,dpi=480")
@Preview(name = "tablet", device = "spec:shape=Normal,width=1280,height=800,unit=dp,dpi=480")
annotation class DevicePreviews*/

@Preview(name = "phone", device = Devices.PHONE)
@Preview(
    name = "landscape",
    device = "spec:width=640dp,height=360dp,dpi=420",
)
@Preview(name = "foldable", device = Devices.FOLDABLE)
@Preview(name = "tablet", device = Devices.TABLET)
annotation class DevicePreviews

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light theme")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark theme")
annotation class ThemePreviews

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light theme", showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark theme", showBackground = true)
annotation class ThemePreviewsWithBackground

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light theme", showSystemUi = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark theme", showSystemUi = true)
annotation class ThemePreviewsWithShowSystemUi


@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "Light theme",
    showSystemUi = true, showBackground = true
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark theme",
    showSystemUi = true, showBackground = true
)
annotation class ThemePreviewsWithShowBackgroundAndShowSystemUi


@Composable
fun AppThemedPreview(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    AppTheme(darkTheme = darkTheme, dynamicColor = dynamicColor) {
        Surface{
            Box(Modifier.padding(10.dp)){
                content()
            }
        }
    }
}


