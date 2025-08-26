package com.example.designsystem.theme

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview


/**
 * Multipreview annotation that represents light and dark themes. Add this annotation to a
 * composable to render the both themes.
 */
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light theme")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark theme")
annotation class ThemePreviews


@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light theme", showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark theme", showBackground = true)
annotation class ThemePreviewsWithBackground


@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light theme", showSystemUi = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark theme", showSystemUi = true)
annotation class ThemePreviewsWithShowSystemUi


@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light theme", showSystemUi = true, showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark theme", showSystemUi = true, showBackground = true)
annotation class ThemePreviewsWithShowBackgroundAndShowSystemUi