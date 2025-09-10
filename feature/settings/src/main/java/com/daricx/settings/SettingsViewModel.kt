package com.daricx.settings


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.settings.SettingsRepository
import com.example.model.settings.AppCurrency
import com.example.model.settings.AppLanguage
import com.example.model.settings.AppSettings
import com.example.model.settings.AppThemeOption
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repo: SettingsRepository
) : ViewModel() {

    val settings: StateFlow<AppSettings> = repo.settings
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), AppSettings())

    fun changeTheme(t: AppThemeOption) = viewModelScope.launch { repo.setTheme(t) }
    fun toggleDynamicColor(e: Boolean) = viewModelScope.launch { repo.setDynamicColor(e) }
    fun changeLanguage(l: AppLanguage) = viewModelScope.launch { repo.setLanguage(l) }
    fun changeCurrency(c: AppCurrency) = viewModelScope.launch { repo.setCurrency(c) }
    fun toggleForceRtl(e: Boolean) = viewModelScope.launch { repo.setForceRtl(e) }
}
