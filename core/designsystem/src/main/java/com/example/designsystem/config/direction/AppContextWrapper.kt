package com.example.designsystem.config.direction

import android.annotation.TargetApi
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.os.Build
import java.util.*

class AppContextWrapper(base: Context) : ContextWrapper(base) {

    companion object {
        @SuppressWarnings("deprecation")
        fun wrap(context: Context, language: String): ContextWrapper {
            val config: Configuration = context.resources.configuration
            val sysLocale: Locale
            sysLocale = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
                getSystemLocale(config)
            } else {
                getSystemLocaleLegacy(config)
            }
            if (!language.isEmpty() && sysLocale.language != language) {
                val locale = Locale(language)
                Locale.setDefault(locale)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    setSystemLocale(config, locale)
                } else {
                    setSystemLocaleLegacy(config, locale)
                }
            }
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                context.createConfigurationContext(config)
                AppContextWrapper(context)
            } else {
                context.resources.updateConfiguration(config, context.resources.displayMetrics)
                AppContextWrapper(context)
            }
        }

        @SuppressWarnings("deprecation")
        fun getSystemLocaleLegacy(config: Configuration): Locale {
            return config.locale
        }

        @TargetApi(Build.VERSION_CODES.N)
        fun getSystemLocale(config: Configuration): Locale {
            return config.locales[0]
        }

        @SuppressWarnings("deprecation")
        fun setSystemLocaleLegacy(config: Configuration, locale: Locale) {
            config.locale = locale
        }

        @TargetApi(Build.VERSION_CODES.N)
        fun setSystemLocale(config: Configuration, locale: Locale) {
            config.setLocale(locale)
        }
    }
}