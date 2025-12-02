package com.daricx.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.pm.ApplicationInfo
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy.Builder
import android.util.Log
import com.daricx.markets.di.MarketsModuleCompositions
import com.daricx.settings.di.SettingsModuleCompositions
import com.daricx.ui.di.CompositionUiModule
import com.example.common.di.CommonModuleComposition
import com.example.data.di.DataModuleComposition
import timber.log.Timber
import timber.log.Timber.Forest.plant
import java.util.concurrent.Executors
import org.koin.android.ext.koin.androidContext
import org.koin.core.annotation.KoinApplication
import org.koin.ksp.generated.*

@KoinApplication(
    modules = [
        DataModuleComposition::class,
        CommonModuleComposition::class,
        MarketsModuleCompositions::class,
        SettingsModuleCompositions::class,
        CompositionUiModule::class,
    ]
)
class Application() : Application(){
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@Application)
        }

        //setStrictModePolicy()

        if (isDebuggable()) {
            plant(CustomizedDebugTree("Timber"))
            //plant(DebugTree())
        } else {
            plant(CrashReportingTree())
        }

        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            Timber.e(throwable.toString() + "Uncaught exception in thread: ${thread.name}")
        }
    }

    /** A tree which logs important information for crash reporting.  */
    private class CrashReportingTree : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return
            }
            //FakeCrashLibrary.log(priority, tag, message) ********
            /*t?.let {
                when (priority) {
                    Log.ERROR -> FakeCrashLibrary.logError(it)
                    Log.WARN -> FakeCrashLibrary.logWarning(it)
                }
            }*/
        }
    }


    private fun isDebuggable(): Boolean {
        return 0 != applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE
    }


    @SuppressLint("NewApi")
    private fun setStrictModePolicy() {
        if (isDebuggable()) {
            StrictMode.setThreadPolicy(
                Builder().detectAll().penaltyLog().penaltyListener(Executors.newSingleThreadExecutor()){
                    Timber.tag("setStrictModePolicy").e(it)
                }.build(),
            )
        }
    }

}


class CustomizedDebugTree(private val prefix: String) : Timber.DebugTree() {

    override fun createStackElementTag(element: StackTraceElement): String {
        return "$prefix | File:${element.fileName} | Line:${element.lineNumber} | Method:${element.methodName}"
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        val jsonMessage = """
            {
                "message": "$message",
                "tag": "$tag",
            }
        """.trimIndent()
        super.log(priority, tag, jsonMessage, t)
    }
}



