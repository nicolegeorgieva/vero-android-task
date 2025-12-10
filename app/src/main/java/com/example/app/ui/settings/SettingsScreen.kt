package com.example.app.ui.settings

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SettingsScreen() {
  val viewModel: SettingsViewModel = hiltViewModel()
  SettingsUi(
    uiState = viewModel.uiState(),
    onEvent = viewModel::onEvent,
  )
}

@Composable
fun SettingsUi(
  uiState: SettingsState,
  onEvent: (SettingsEvent) -> Unit
) {
  // TODO
}
