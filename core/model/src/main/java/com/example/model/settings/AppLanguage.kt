package com.example.model.settings

sealed interface AppLanguage {
    val code: String
    val englishName: String
    val localName: String get() = englishName

    data object English : AppLanguage {
        override val code = "en"
        override val englishName = "English"
    }

    data object Persian : AppLanguage {
        override val code = "fa"
        override val englishName = "Persian"
        override val localName = "فارسی"
    }

    data class Custom(override val code: String) : AppLanguage {
        override val englishName: String = code.uppercase()
    }

    companion object {
        fun from(code: String?): AppLanguage = when (code?.lowercase()) {
            English.code -> English
            Persian.code -> Persian
            null, "" -> English
            else -> Custom(code)
        }

        val supported: List<AppLanguage> = listOf(English, Persian)
    }
}

val AppLanguage.displayName: String get() = when (this) {
    is AppLanguage.English -> englishName
    is AppLanguage.Persian -> localName
    is AppLanguage.Custom -> englishName
}
