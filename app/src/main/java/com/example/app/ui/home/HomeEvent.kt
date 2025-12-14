package com.example.app.ui.home

sealed interface HomeEvent {
  data class SearchTextChange(val text: String) : HomeEvent
  data object ScanQrCodeClick : HomeEvent
  data class ScanQrCode(val scannedText: String) : HomeEvent
  data object CloseQrCodeScanner : HomeEvent
  data object SettingsClick : HomeEvent
  data object RefreshTasks : HomeEvent
  data object RetryClick : HomeEvent
}