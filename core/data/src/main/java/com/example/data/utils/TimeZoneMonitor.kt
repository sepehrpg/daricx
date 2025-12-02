package com.example.data.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import androidx.tracing.trace
import com.example.data.di.ApplicationScope
import com.example.data.di.qualifier.AppDispatcher
import com.example.data.di.qualifier.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.shareIn
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toKotlinTimeZone
import org.koin.core.annotation.Single
import java.time.ZoneId



interface TimeZoneMonitor {
    val currentTimeZone: Flow<TimeZone>
}

@Single(binds = [TimeZoneMonitor::class])
class TimeZoneBroadcastMonitor(
    private val context: Context,
    @ApplicationScope private val appScope: CoroutineScope,
    @Dispatcher(AppDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
) : TimeZoneMonitor {

    override val currentTimeZone: SharedFlow<TimeZone> =
        callbackFlow {
            // Send default time zone first
            trySend(TimeZone.currentSystemDefault())

            val receiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    if (intent.action != Intent.ACTION_TIMEZONE_CHANGED) return

                    val zoneIdFromIntent = if (VERSION.SDK_INT < VERSION_CODES.R) {
                        null
                    } else {
                        intent.getStringExtra(Intent.EXTRA_TIMEZONE)?.let { timeZoneId ->
                            val zoneId = ZoneId.of(timeZoneId, ZoneId.SHORT_IDS)
                            zoneId.toKotlinTimeZone()
                        }
                    }

                    trySend(zoneIdFromIntent ?: TimeZone.currentSystemDefault())
                }
            }

            trace("TimeZoneBroadcastReceiver.register") {
                context.registerReceiver(receiver, IntentFilter(Intent.ACTION_TIMEZONE_CHANGED))
            }

            // Send again after registering receiver
            trySend(TimeZone.currentSystemDefault())

            awaitClose {
                context.unregisterReceiver(receiver)
            }
        }
            .distinctUntilChanged()
            .conflate()
            .flowOn(ioDispatcher)
            .shareIn(appScope, SharingStarted.WhileSubscribed(5_000), replay = 1)
}
