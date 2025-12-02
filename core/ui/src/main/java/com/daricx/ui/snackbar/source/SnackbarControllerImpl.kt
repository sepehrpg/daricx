package com.daricx.ui.snackbar.source

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import org.koin.core.annotation.Single
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Sample Usage:
 *
 * 1- Composable app (root)
 * @Composable
 * fun DaricxApp(
 *     snackbarController: SnackbarController, //
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
@Single(binds = [SnackbarController::class])
class SnackbarControllerImpl  : SnackbarController {
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