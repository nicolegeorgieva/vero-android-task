package com.example.app.ui.home

sealed interface HomeEvent {
  data object SettingsClick : HomeEvent
  data object RefreshTasks : HomeEvent
  data object RetryClick : HomeEvent
}