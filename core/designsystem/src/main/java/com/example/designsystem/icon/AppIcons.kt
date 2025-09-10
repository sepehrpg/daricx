
package com.example.designsystem.icon

import androidx.compose.material.icons.Icons
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
import androidx.compose.material.icons.outlined.SwapHoriz
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.designsystem.R


object AppIcons {

    val AccountCircleIcon = Icons.Rounded.AccountCircle
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

    val NotificationIcon: ImageVector
        @Composable get() = ImageVector.vectorResource(R.drawable.core_designsystem_notification)

}
