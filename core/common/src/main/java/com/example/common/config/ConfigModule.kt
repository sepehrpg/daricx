package com.example.common.config

import android.content.Context
import android.os.Build
import org.koin.core.annotation.Module
import org.koin.core.annotation.Qualifier
import org.koin.core.annotation.Single

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppVersionName

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppVersionCode

@Module
class ConfigModule {

    @Single
    @AppVersionName
    fun provideAppVersionName(context: Context): String {
        return try {
            val pm = context.packageManager
            val pkgName = context.packageName
            val packageInfo = pm.getPackageInfo(pkgName, 0)
            packageInfo.versionName ?: "N/A"
        } catch (e: Exception) {
            "N/A"
        }
    }

    @Single
    @AppVersionCode
    fun provideAppVersionCode(context: Context): String {
        return try {
            val pm = context.packageManager
            val pkgName = context.packageName
            val packageInfo = pm.getPackageInfo(pkgName, 0)

            val versionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                packageInfo.longVersionCode
            } else {
                @Suppress("DEPRECATION")
                packageInfo.versionCode.toLong()
            }
            versionCode.toString()
        } catch (e: Exception) {
            "0"
        }
    }
}
