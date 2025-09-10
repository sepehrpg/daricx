package com.example.designsystem.theme

import androidx.compose.ui.graphics.Color




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


/*


import androidx.compose.ui.graphics.Color

*/
/**
 * Official brand (verified):
 * - CMC Blue: #3861FB  (source: svgmix.com item yWWRRy)
 *
 * The rest are "CMC-Inspired" to match the live UI feel; adjust freely if needed.
 *//*


*/
/* ---------- Brand & Status ---------- *//*

val CmcBlue = Color(0xFF3861FB)      // Primary brand (links, CTAs)
val CmcGreen = Color(0xFF16C784)     // Price up (CMC-like)
val CmcRed   = Color(0xFFEA3943)     // Price down (CMC-like)

*/
/* ---------- Neutrals (Light) ---------- *//*

val L_Background       = Color(0xFFFCFDFF) // page bg (خیلی روشن، متمایل به آبی)
val L_Surface          = Color(0xFFFFFFFF) // کارت/ورق سفید
val L_SurfaceVariant   = Color(0xFFEEF2F5) // ردیف جدول/دیوايدر روشن
val L_TextPrimary      = Color(0xFF0B1426) // مشکیِ متمایل به سرمه‌ای (CMC-ish)
val L_TextSecondary    = Color(0xFF58667E) // خاکستری متنی
val L_IconGray         = Color(0xFFA5B0C2)
val L_Border           = Color(0xFFEFF2F5)

val L_BlueContainer    = Color(0xFFE7EFFF)
val L_GreenContainer   = Color(0xFFDCFBEF)
val L_RedContainer     = Color(0xFFFCE6E8)

*/
/* ---------- Neutrals (Dark) ---------- *//*

val D_Background       = Color(0xFF0B1426) // پس‌زمینه تیرهٔ سرمه‌ای
val D_Surface          = Color(0xFF0E1629) // کارت تیره
val D_SurfaceVariant   = Color(0xFF1A2336) // ردیف/جدول تیره‌تر
val D_TextPrimary      = Color(0xFFF1F4F8) // متن اصلی روشن
val D_TextSecondary    = Color(0xFFA7B1C2) // متن ثانویه
val D_Border           = Color(0xFF25324A)

val D_BlueContainer    = Color(0xFF203B92) // آبی ظرف در تیره
val D_GreenContainer   = Color(0xFF0F2F24)
val D_RedContainer     = Color(0xFF3B1113)

*/
/* ---------- Material 3 Surface containers (Light) ---------- *//*

val L_SurfaceDim               = Color(0xFFF6F8FC)
val L_SurfaceBright            = Color(0xFFFFFFFF)
val L_SurfaceContainerLowest   = Color(0xFFFFFFFF)
val L_SurfaceContainerLow      = Color(0xFFFAFBFE)
val L_SurfaceContainer         = Color(0xFFF6F8FC)
val L_SurfaceContainerHigh     = Color(0xFFF1F4F8)
val L_SurfaceContainerHighest  = Color(0xFFECEFF4)

*/
/* ---------- Material 3 Surface containers (Dark) ---------- *//*

val D_SurfaceDim               = Color(0xFF0A1020)
val D_SurfaceBright            = Color(0xFF131A2C)
val D_SurfaceContainerLowest   = Color(0xFF070B14)
val D_SurfaceContainerLow      = Color(0xFF0C1222)
val D_SurfaceContainer         = Color(0xFF10182A)
val D_SurfaceContainerHigh     = Color(0xFF142033)
val D_SurfaceContainerHighest  = Color(0xFF19263B)
*/
