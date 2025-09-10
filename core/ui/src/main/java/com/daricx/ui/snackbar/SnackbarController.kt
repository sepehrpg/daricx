package com.daricx.ui.snackbar


import androidx.compose.material3.SnackbarDuration
import com.example.designsystem.component.snackbar.AppSnackbarType
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Sample Usage:
 *
 * 1- Composable app (root)
 * @Composable
 * fun DaricxApp(
 *     snackbarController: SnackbarController, // ا
 *     content: @Composable () -> Unit
 * ) {
 *     val hostState = remember { SnackbarHostState() }
 *     val lifecycle = LocalLifecycleOwner.current.lifecycle
 *     val scope = rememberCoroutineScope()
 *     CompositionLocalProvider(LocalSnackbarController provides snackbarController) {
 *         Scaffold(
 *             snackbarHost = {
 *                 AppSnackbarHost(
 *                     hostState = hostState,
 *                     modifier = Modifier
 *                         .fillMaxWidth()
 *                         .padding(16.dp)
 *                     // content = { data, type, msg ->
 *                     //     ...
 *                     // }
 *                 )
 *             }
 *         ) { _ ->
 *             content()
 *         }
 *         LaunchedEffect(Unit) {
 *             lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
 *                 snackbarController.requests.collect { req ->
 *                     scope.launch {
 *                         hostState.showAppSnackbar(
 *                             message = req.message,
 *                             type = req.type,
 *                             actionLabel = req.actionLabel,
 *                             withDismissAction = req.withDismissAction,
 *                             duration = req.duration
 *                         )
 *                     }
 *                 }
 *             }
 *         }
 *     }
 * }
 *
 * 2-Activity:
 * @AndroidEntryPoint
 * class MainActivity : ComponentActivity() {
 *     @Inject lateinit var snackbarController: SnackbarController
 *
 *     override fun onCreate(savedInstanceState: Bundle?) {
 *         super.onCreate(savedInstanceState)
 *         setContent {
 *             DaricxApp(snackbarController = snackbarController) {
 *                 // NavHost(...)
 *             }
 *         }
 *     }
 * }
 *
 * 3-ViewModel:
 * @HiltViewModel
 * class MarketsViewModel @Inject constructor(
 *     private val snack: SnackbarController
 * ) : ViewModel() {
 *     fun onWatchlistUpdated() {
 *         snack.showSuccess("Watchlist updated") // Non-susp
 *     }
 *     fun onNetworkError() {
 *         snack.showError("Network error", action = "Retry")
 *     }
 * }
 *
 *
 * 4-Each Component :
 * @Composable
 * fun SomeComposable() {
 *     val snack = LocalSnackbarController.current
 *     Button(onClick = { snack.showInfo("Loading markets…") }) {
 *         Text("Show Snackbar")
 *     }
 * }
 */

data class SnackbarRequest(
    val message: String,
    val type: AppSnackbarType = AppSnackbarType.Default,
    val actionLabel: String? = null,
    val withDismissAction: Boolean = true,
    val duration: SnackbarDuration = SnackbarDuration.Short
)

interface SnackbarController {
    val requests: SharedFlow<SnackbarRequest>

    fun show(request: SnackbarRequest)

    fun showInfo(message: String, action: String? = null) =
        show(SnackbarRequest(message, AppSnackbarType.Info, action))
    fun showSuccess(message: String, action: String? = null) =
        show(SnackbarRequest(message, AppSnackbarType.Success, action))
    fun showWarning(message: String, action: String? = null) =
        show(SnackbarRequest(message, AppSnackbarType.Warning, action))
    fun showError(message: String, action: String? = null) =
        show(SnackbarRequest(message, AppSnackbarType.Error, action))
}

@Singleton
class SnackbarControllerImpl @Inject constructor() : SnackbarController {
    private val _requests = MutableSharedFlow<SnackbarRequest>(
        replay = 0,
        extraBufferCapacity = 64,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    override val requests: SharedFlow<SnackbarRequest> = _requests

    override fun show(request: SnackbarRequest) {
        _requests.tryEmit(request)
    }
}
