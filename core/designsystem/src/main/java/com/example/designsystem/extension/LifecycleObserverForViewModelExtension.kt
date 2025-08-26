package com.example.designsystem.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver


@Composable
fun <viewModel : LifecycleObserver> viewModel.observeLifecycleEvents(lifecycle: Lifecycle) {
    DisposableEffect(lifecycle) {
        lifecycle.addObserver(this@observeLifecycleEvents)
        onDispose {
            lifecycle.removeObserver(this@observeLifecycleEvents)
        }
    }
}
/**
 * EXAMPLE USE IN VIEWMODEL::
 * class FirstViewModel: ViewModel(), DefaultLifecycleObserver {
 *
 *     private val TAG = "FirstViewModel"
 *     override fun onCreate(owner: LifecycleOwner) {
 *         super.onCreate(owner)
 *         Log.d(TAG, "onCreate")
 *     }
 *
 *     override fun onResume(owner: LifecycleOwner) {
 *         super.onResume(owner)
 *         Log.d(TAG, "onResume")
 *     }
 *
 *     override fun onStart(owner: LifecycleOwner) {
 *         super.onStart(owner)
 *         Log.d(TAG, "onStart")
 *     }
 *
 *     override fun onPause(owner: LifecycleOwner) {
 *         super.onPause(owner)
 *         Log.d(TAG, "onPause")
 *     }
 *
 *     override fun onStop(owner: LifecycleOwner) {
 *         super.onStop(owner)
 *         Log.d(TAG, "onStop")
 *     }
 *
 *     override fun onDestroy(owner: LifecycleOwner) {
 *         super.onDestroy(owner)
 *         Log.d(TAG, "onDestroy")
 *     }
 * }
 *
 * AND THEN :: viewModel.observeLifecycleEvents(LocalLifecycleOwner.current.lifecycle)
 * **/