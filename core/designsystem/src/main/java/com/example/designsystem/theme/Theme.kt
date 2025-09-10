package com.example.designsystem.theme

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.designsystem.config.isPersian


@VisibleForTesting
val LightColorScheme = lightColorScheme(
    primary = PrimaryColor,
    onPrimary = White,
    primaryContainer = Blue50,
    onPrimaryContainer = Blue30,
    inversePrimary = Blue40,

    secondary = SecondaryColor,
    onSecondary = White,
    secondaryContainer = Green50 ,
    onSecondaryContainer = Green30,

    tertiary = TertiaryColor,
    onTertiary = White,
    tertiaryContainer = Red50 ,
    onTertiaryContainer = Red30 ,

    error = Red30,
    onError = White,
    errorContainer = Red50 ,
    onErrorContainer =Red30 ,

    background = White,
    onBackground = Black20,

    surface = White30,
    onSurface = Black50,
    surfaceVariant = Gray50,
    onSurfaceVariant = Gray20,

    surfaceTint = Gray30 ,

    outline = GrayAndroid,
    outlineVariant = Gray100,

    surfaceContainer = White,
    surfaceContainerLow = White,
    surfaceContainerLowest = White,
    surfaceContainerHigh = White,
    surfaceContainerHighest = White,

    //surfaceBright = ,
    //surfaceDim = ,
    //inverseSurface = ,
    //inverseOnSurface = ,
)


@VisibleForTesting
val DarkColorScheme = darkColorScheme(
    primary = PrimaryColor,
    onPrimary = White,
    primaryContainer = Blue20,
    onPrimaryContainer = Blue40,
    inversePrimary = Blue40,

    secondary = SecondaryColor,
    onSecondary = White,
    secondaryContainer = Green20 ,
    onSecondaryContainer = Green30,

    tertiary = TertiaryColor,
    onTertiary = White,
    tertiaryContainer = Red20 ,
    onTertiaryContainer = Red30 ,

    error = Red20,
    onError = White,
    errorContainer = Red20 ,
    onErrorContainer =Red30 ,

    background = Black60,
    onBackground = White,

    surface = Black30,
    onSurface = White40,
    surfaceVariant = Gray10,
    onSurfaceVariant = Gray20 ,

    surfaceTint = Gray15 ,
    //surfaceTint = Gray20 ,

    outline = GrayAndroid,
    outlineVariant = Gray10,

    surfaceContainer = Black60,
    surfaceContainerLow = Black60,
    surfaceContainerLowest = Black60,
    surfaceContainerHigh = Black60,
    surfaceContainerHighest = Black60,

    //surfaceBright = ,
    //surfaceDim = ,
    //inverseSurface = ,
    //inverseOnSurface = ,
)


@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val typography = if (isPersian()) {
        buildTypography (AppFontPersian)
    } else {
        buildTypography()
    }

    //val typography = appBuildTypography(AppFontEnglish)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content
    )
}

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
fun supportsDynamicTheming() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
