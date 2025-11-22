
package com.example.designsystem.icon

import android.media.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Diamond
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.PriceChange
import androidx.compose.material.icons.outlined.RocketLaunch
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.SwapHoriz
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.CandlestickChart
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material.icons.rounded.Language
import androidx.compose.material.icons.rounded.SwapVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.designsystem.R


object AppIcons {

    val AccountCircleIcon = Icons.Rounded.AccountCircle
    val KeyboardArrowLeft = Icons.AutoMirrored.Rounded.KeyboardArrowLeft
    val KeyboardArrowUp = Icons.Rounded.KeyboardArrowUp
    val KeyboardArrowDown = Icons.Rounded.KeyboardArrowDown
    val PriceChangeIcon = Icons.Outlined.PriceChange
    val SwapHorizIcon = Icons.Outlined.SwapHoriz
    val DiamondIcon = Icons.Outlined.Diamond
    val RocketLaunchIcon =  Icons.Outlined.RocketLaunch
    val ChevronRightIcon = Icons.Outlined.ChevronRight
    val LanguageIcon = Icons.Outlined.Language
    val BadgeIcon = Icons.Outlined.Badge
    val CloseIcon =  Icons.Filled.Close
    val ErrorIcon =  Icons.Filled.Error
    val CheckCircleIcon = Icons.Filled.CheckCircle
    val InfoIcon = Icons.Filled.Info
    val WarningIcon = Icons.Filled.Warning
    val List = Icons.AutoMirrored.Filled.List
    val Share = Icons.Outlined.Share
    val SwapVert = Icons.Rounded.SwapVert
    val CandlestickChart = Icons.Rounded.CandlestickChart
    val Website = Icons.Rounded.Language
    val Github : ImageVector
        @Composable get() = ImageVector.vectorResource(R.drawable.core_designsystem_github)

    val Telegram : ImageVector
        @Composable get() = ImageVector.vectorResource(R.drawable.core_designsystem_telegram)

    val XTwitter : ImageVector
        @Composable get() = ImageVector.vectorResource(R.drawable.core_designsystem_x_ogo)

    val FaceBook : ImageVector
        @Composable get() = ImageVector.vectorResource(R.drawable.core_designsystem_facebook)

    val Reddit : ImageVector
        @Composable get() = ImageVector.vectorResource(R.drawable.core_designsystem_reddit)

    val WhitePaper : ImageVector
        @Composable get() = ImageVector.vectorResource(R.drawable.core_designsystem_rounded_contract_24)


    val NotificationIcon: ImageVector
        @Composable get() = ImageVector.vectorResource(R.drawable.core_designsystem_notification)

    val Search: ImageVector
        @Composable get() = ImageVector.vectorResource(R.drawable.core_designsystem_search)

    val Star: ImageVector
        @Composable get() = ImageVector.vectorResource(R.drawable.core_designsystem_star)

    val StarFill: ImageVector
        @Composable get() = ImageVector.vectorResource(R.drawable.core_designsystem_fill_star)

    val ChartType1: ImageVector
        @Composable get() = ImageVector.vectorResource(R.drawable.core_designsystem_chart1)

}
