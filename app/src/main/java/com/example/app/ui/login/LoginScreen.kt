package com.example.app.ui.login

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LoginScreen() {
  val viewModel: LoginViewModel = hiltViewModel()

  LoginUi(
    uiState = viewModel.uiState(),
    onEvent = viewModel::onEvent,
  )
}

@Composable
fun LoginUi(
  uiState: LoginState,
  onEvent: (LoginEvent) -> Unit
) {
  // TODO
}
