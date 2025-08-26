package com.example.common.config // Or your desired package

import android.content.Context
import android.os.Build
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppVersionName

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppVersionCode

@Module
@InstallIn(SingletonComponent::class)
object ConfigModule {

    // This provider gets the app's version name safely from the application context.
    @Provides
    @Singleton
    @AppVersionName
    fun provideAppVersionName(@ApplicationContext context: Context): String {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            // Using "N/A" or "0" is a matter of preference for the fallback value.
            packageInfo.versionName ?: "N/A"
        } catch (e: Exception) {
            "N/A" // Fallback in case of an error
        }
    }

    // This provider gets the app's version code safely.
    @Provides
    @Singleton
    @AppVersionCode
    fun provideAppVersionCode(@ApplicationContext context: Context): String {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            val versionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                packageInfo.longVersionCode
            } else {
                @Suppress("DEPRECATION")
                packageInfo.versionCode.toLong()
            }
            versionCode.toString()
        } catch (e: Exception) {
            "0" // Fallback in case of an error
        }
    }
}