package com.example.app.ui.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.app.common.ComposeViewModel
import com.example.app.domain.SessionUseCase
import com.example.app.navigation.Navigator
import com.example.app.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
  private val navigator: Navigator,
  private val sessionUseCase: SessionUseCase,
) : ComposeViewModel<SettingsState, SettingsEvent>() {
  private var logoutButtonLoading by mutableStateOf(false)

  @Composable
  override fun uiState(): SettingsState {
    return SettingsState(
      logoutButtonLoading = logoutButtonLoading,
    )
  }

  override fun onEvent(event: SettingsEvent) {
    when (event) {
      SettingsEvent.BackClick -> handleBackClick()
      SettingsEvent.LogoutClick -> handleLogoutClick()
    }
  }

  private fun handleBackClick() {
    viewModelScope.launch {
      navigator.back()
    }
  }

  private fun handleLogoutClick() {
    viewModelScope.launch {
      logoutButtonLoading = true
      sessionUseCase.logout()
      navigator.replace(listOf(Screen.Login))
      logoutButtonLoading = false
    }
  }
}