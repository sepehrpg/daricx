package com.example.designsystem.theme

import androidx.compose.ui.graphics.Color


val StarColor = Color(0xFFF5BB7F)

internal val White = Color(0xFFFFFFFF)
internal val White30 = Color(0xFFFCFDFF) //  LightTheme :  main background
internal val White40 = Color(0xFFF3F3F4) //
internal val White50 = Color(0xFFEDEDEE) //

internal val Gray10 = Color(0xFF323546) // DarkTheme :  Card background
internal val Gray15 = Color(0xFF636A7F) // DarkTheme : icon color
internal val Gray20 = Color(0xFF919AAA) // dark gray on background gray
internal val Gray30 = Color(0xFFA5B0C2) //  LightTheme : icon or text color
internal val Gray50 = Color(0xFFEEF2F5) //  LightTheme : Card background
internal val Gray100 = Color(0xFFEEEEEE) // Extra Light Gray
internal val GrayAndroid = Color.Gray
internal val GrayLightAndroid = Color.LightGray
internal val GrayDarkAndroid = Color.DarkGray

internal val Black = Color(0xFF000000)
internal val Black20 = Color(0xFF0C1320) //  LightTheme : bold text
internal val Black30 = Color(0xFF171925) // DarkTheme : Main background
internal val Black50 = Color(0xFF151C27) //  LightTheme : text
internal val Black60 = Color(0xFF222431) // DarkTheme : Main background soft

internal val Blue20 = Color(0xFF1F2A58) // DarkTheme : on background primary
internal val Blue30 = Color(0xFF3861FA) // LightTheme :primary
internal val Blue40 = Color(0xFF5F84F9) // DarkTheme : primary
internal val Blue50 = Color(0xFFE7EFFF) // LightTheme : on background primary

internal val Green20 = Color(0xFF173B37) // DarkTheme :  Green background
internal val Green30 = Color(0xFF16C683) // Green
internal val Green50 = Color(0xFFDCFBEF) //  LightTheme : Background Green

internal val Red20 = Color(0xFF3F1F2A) // DarkTheme :  Background Red
internal val Red30 = Color(0xFFE93943) // Red
internal val Red50 = Color(0xFFFCE6E8) // LightTheme : Background Red


internal val PrimaryColor = Blue30
internal val SecondaryColor = Green30
internal val TertiaryColor = Red30



/**
 * CoinMarketCap-like palette (best-effort).
 *
 * Sources:
 * - Brand blue #3861FB (logotype listing) – verified.
 * - Green #16C784, Red #EA3943, Surfaces & grays – observed/common in CMC UI clones (approx).
 *
 * If you want pixel-perfect parity, sample from live DOM with DevTools.
 */

// Brand / Primary
val CmcBlue500      = Color(0xFF3861FB) // verified brand blue (close to #3861FA)
val CmcBlue50       = Color(0xFFE7EFFF) // light tint for containers (approx)

// Success / Error (price up/down)
val CmcGreen500     = Color(0xFF16C784) // price up (approx, widely used)
val CmcGreen50      = Color(0xFFDCFBEF) // success container bg (tint, approx)
val CmcRed500       = Color(0xFFEA3943) // price down (approx, widely used)
val CmcRed50        = Color(0xFFFCE6E8) // error container bg (tint, approx)

// Backgrounds & text
val CmcBg0          = Color(0xFFFCFDFF) // overall app bg (very close to white)
val CmcSurface      = Color(0xFFFFFFFF) // pure white surface (cards/dialogs)
val CmcSurfaceAlt   = Color(0xFFEEF2F5) // alt surface used a lot in CMC tables/cards (≈EFF2F5)
val CmcInk900       = Color(0xFF0C1320) // primary text (almost-black, close to CMC)
val CmcInk800       = Color(0xFF151C27) // dark neutral for elevated surfaces

// Grays
val CmcGray600      = Color(0xFF919AAA) // onSurfaceVariant
val CmcGray500      = Color(0xFFA5B0C2) // icons/outline
val CmcGray100      = Color(0xFFEEEEEE) // dividers/very light lines


