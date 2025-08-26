package com.example.designsystem.component

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalAccessibilityManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.designsystem.theme.Red20
import com.example.designsystem.theme.Green20
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow


@Composable
fun AppSnackBarPrimary(
    snackBarType: SnackBarType,
    message: String,
    parentModifier: Modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp),
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .background(
            color = if (snackBarType == SnackBarType.ERROR) Red20 else  Green20,
            shape = RoundedCornerShape(10.dp)
        ),
    alignment:Alignment =  Alignment.TopCenter,
    isRtl: Boolean = false,
    performAction: () -> Unit
) {
    CompositionLocalProvider(
        LocalLayoutDirection provides
                if (isRtl) LayoutDirection.Rtl else LayoutDirection.Ltr
    ) {
        Box(parentModifier.fillMaxSize(), contentAlignment = alignment){
            Column(modifier = modifier) {
                Row(
                    Modifier.padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(Modifier.weight(1f)) {
                        Text(
                            text = message,
                            fontSize = 14.sp,
                            color = if (snackBarType == SnackBarType.ERROR) Color.White else Color.White,
                            modifier = Modifier
                                .padding(horizontal = 10.dp)
                                .fillMaxWidth()
                        )
                    }
                    Box() {
                        Icon(
                            imageVector = if (snackBarType == SnackBarType.ERROR) Icons.Filled.Error else Icons.Filled.Done,
                            contentDescription = "",
                            tint = if (snackBarType == SnackBarType.ERROR) Color.White else Color.White
                        )
                    }

                }
            }
        }
    }
}





@Composable
fun AppSnackBarSecondary(snackBarType: SnackBarType, message: String, performAction: () -> Unit) {
    Column(Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Bottom) {
        Box(
            Modifier
                .fillMaxWidth()
                .background(color = if (snackBarType == SnackBarType.ERROR) Red20 else Green20)
        ) {
            Row(
                Modifier.padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(Modifier.weight(1f)) {
                    Text(
                        text = message,
                        fontSize = 14.sp,
                        color = if (snackBarType == SnackBarType.ERROR) Color.White else Color.White,
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .fillMaxWidth()
                    )
                }
                Box() {
                    Icon(
                        imageVector = if (snackBarType == SnackBarType.ERROR) Icons.Filled.Error else Icons.Filled.Done,
                        contentDescription = "",
                        tint = if (snackBarType == SnackBarType.ERROR) Color.White else Color.White
                    )
                }

            }
        }
    }
}


@Composable
fun AppSnackBarTertiary(snackBarType: SnackBarType, message: String, performAction: () -> Unit) {
    Column(verticalArrangement = Arrangement.Bottom) {
        Box(
            Modifier
                //.padding(horizontal = 20.dp, vertical = 20.dp)
                //.background(color = if (status=="ok") doneColorSnackBAr else errorColorSnackBar, shape = RoundedCornerShape(10.dp))
                .background(
                    color = if (snackBarType == SnackBarType.ERROR) Red20  else if (snackBarType == SnackBarType.INFO) Color(
                        0xFF1971C2
                    ) else Green20
                )
        ) {
            Row(
                Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box() {
                    Icon(
                        imageVector = if (snackBarType == SnackBarType.ERROR) Icons.Filled.Error else Icons.Filled.Done,
                        contentDescription = "",
                        tint = if (snackBarType == SnackBarType.ERROR) Color.White else Color.White
                    )
                }
                Box(Modifier.weight(1f), contentAlignment = Alignment.CenterEnd) {
                    Text(
                        text = message,
                        fontSize = 14.sp,
                        color = if (snackBarType == SnackBarType.ERROR) Color.White else Color.White,
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .fillMaxWidth(),
                    )
                }
            }
        }
    }
}



@Composable
fun MultiSnackBarHost(
    snackBarMessages: List<String>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        snackBarMessages.forEach { message ->
            Snackbar(
                modifier = Modifier.fillMaxWidth(),

            ) {
                Text(message)
            }
        }
    }
}




@Preview(showSystemUi = true)
@Composable
private fun AppSnackBarPrimaryPreview() {
    AppSnackBarPrimary(snackBarType = SnackBarType.ERROR, message = "Test") {}
}

@Preview(showSystemUi = true)
@Composable
private fun AppSnackBarSecondaryPreview() {
    AppSnackBarSecondary(SnackBarType.ERROR, "Test 2", {})
}

@Preview(showSystemUi = true)
@Composable
private fun AppSnackBarTertiaryPreview() {
    AppSnackBarTertiary(SnackBarType.ERROR, "Test 2", {})
}


@Preview(showSystemUi = true)
@Composable
private fun MultiSnackBarHostPreview(){
    val snackbarMessages = remember { mutableStateListOf<String>() }
    val snackbarHostState = remember { SnackbarHostState() }


    LaunchedEffect(Unit) {
        snackbarMessages.add("Message 1")
        delay(1000)
        snackbarMessages.add("Message 2")
        delay(1000)
        snackbarMessages.add("Message 3")
        snackbarHostState.showSnackbar(
            message = "",
            duration = SnackbarDuration.Short,
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        SnackbarHost(snackbarHostState){
            MultiSnackBarHost(
                snackBarMessages = snackbarMessages,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}


enum class SnackBarType {
    DEFAULT,
    ERROR,
    INFO,
    ALERT,
}
data class SnackBarMessage(
    val message: String,
    val snackBarType: SnackBarType = SnackBarType.DEFAULT
)
object SnackBarManager {
    private val _snackBarFlow = MutableSharedFlow<SnackBarMessage>()
    val snackBarFlow = _snackBarFlow.asSharedFlow()

    suspend fun showSnackBar(message: String, snackBarType: SnackBarType = SnackBarType.DEFAULT) {
        _snackBarFlow.emit(SnackBarMessage(message, snackBarType))
    }
}


















@Composable
fun SnackBarInDialogContainer(
    text: String,
    actionLabel: String? = null,
    duration: SnackbarDuration =
        if (actionLabel == null) SnackbarDuration.Short else SnackbarDuration.Indefinite,
    dismiss: () -> Unit,
    content: @Composable (SnackbarData) -> Unit
) {
    val snackbarData = remember {
        SnackbarDataImpl(
            SnackbarVisualsImpl(text, actionLabel, true, duration),
            dismiss
        )
    }

    val dur = getDuration(duration, actionLabel)
    if (dur != Long.MAX_VALUE) {
        LaunchedEffect(snackbarData) {
            delay(dur)
            snackbarData.dismiss()
        }
    }

    val popupPosProvider by imeMonitor()
    Popup(
        popupPositionProvider = popupPosProvider,
        properties = PopupProperties(clippingEnabled = false),
    ) {
        content(snackbarData)
    }
}


@Composable
private fun getDuration(duration: SnackbarDuration, actionLabel: String?): Long {
    val accessibilityManager = LocalAccessibilityManager.current
    return remember(duration, actionLabel, accessibilityManager) {
        val orig = when (duration) {
            SnackbarDuration.Short -> 4000L
            SnackbarDuration.Long -> 10000L
            SnackbarDuration.Indefinite -> Long.MAX_VALUE
        }
        accessibilityManager?.calculateRecommendedTimeoutMillis(
            orig, containsIcons = true, containsText = true, containsControls = actionLabel != null
        ) ?: orig
    }
}

/**
 * Monitors the size of the IME (software keyboard) and provides an updating
 * PopupPositionProvider.
 */
@Composable
private fun imeMonitor(): State<PopupPositionProvider> {
    val provider = remember { mutableStateOf(ImePopupPositionProvider(0)) }
    val context = LocalContext.current
    val decorView = remember(context) { context.getActivity()?.window?.decorView }
    if (decorView != null) {
        val ime = remember { WindowInsetsCompat.Type.ime() }
        val bottom = remember { MutableStateFlow(0) }
        LaunchedEffect(Unit) {
            while (true) {
                bottom.value = ViewCompat.getRootWindowInsets(decorView)?.getInsets(ime)?.bottom ?: 0
                delay(33)
            }
        }
        LaunchedEffect(Unit) {
            bottom.collect { provider.value = ImePopupPositionProvider(it) }
        }
    }
    return provider
}

/**
 * Places the popup at the bottom of the screen but above the keyboard.
 * This assumes that the anchor for the popup is in the middle of the screen.
 */
private data class ImePopupPositionProvider(val imeSize: Int): PopupPositionProvider {
    override fun calculatePosition(
        anchorBounds: IntRect, windowSize: IntSize,
        layoutDirection: LayoutDirection, popupContentSize: IntSize
    ) = IntOffset(
        anchorBounds.left + (anchorBounds.width - popupContentSize.width) / 2, // centered on screen
        anchorBounds.top + (anchorBounds.height - popupContentSize.height) / 2 + // centered on screen
                (windowSize.height - imeSize) / 2 // move to the bottom of the screen
    )
}


private fun Context.getActivity(): Activity? {
    var currentContext = this
    while (currentContext is ContextWrapper) {
        if (currentContext is Activity) {
            return currentContext
        }
        currentContext = currentContext.baseContext
    }
    return null
}


private data class SnackbarDataImpl(
    override val visuals: SnackbarVisuals,
    val onDismiss: () -> Unit,
) : SnackbarData {
    override fun performAction() { /* TODO() */ }
    override fun dismiss() { onDismiss() }
}

private data class SnackbarVisualsImpl(
    override val message: String,
    override val actionLabel: String?,
    override val withDismissAction: Boolean,
    override val duration: SnackbarDuration
) : SnackbarVisuals






















