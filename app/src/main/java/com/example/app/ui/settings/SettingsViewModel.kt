package com.example.app.ui.settings

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import com.example.app.common.ComposeViewModel
import com.example.app.navigation.Navigator
import com.example.app.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
  private val navigator: Navigator,
) : ComposeViewModel<SettingsState, SettingsEvent>() {
  @Composable
  override fun uiState(): SettingsState {
    return SettingsState()
  }

  override fun onEvent(event: SettingsEvent) {
    when (event) {
      SettingsEvent.LogoutClick -> handleLogoutClick()
    }
  }

  private fun handleLogoutClick() {
    viewModelScope.launch {
      navigator.navigateTo(Screen.Login)
      // TODO
    }
  }
}