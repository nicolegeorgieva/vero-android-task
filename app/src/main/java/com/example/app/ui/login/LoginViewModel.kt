package com.example.app.ui.login

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import com.example.app.common.ComposeViewModel
import com.example.app.domain.SessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
  private val sessionUseCase: SessionUseCase,
) : ComposeViewModel<LoginState, LoginEvent>() {
  @Composable
  override fun uiState(): LoginState {
    return LoginState()
  }

  override fun onEvent(event: LoginEvent) {
    when (event) {
      is LoginEvent.Login -> handleLogin(event)
    }
  }

  private fun handleLogin(event: LoginEvent.Login) {
    viewModelScope.launch {
      sessionUseCase.login(
        username = event.username,
        password = event.password,
      )
    }
  }
}