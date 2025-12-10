package com.example.app.ui.settings

sealed interface SettingsEvent {
  data object LogoutClick : SettingsEvent
}