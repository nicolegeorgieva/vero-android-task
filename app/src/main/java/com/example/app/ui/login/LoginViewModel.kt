package com.example.app.ui.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import com.example.app.R
import com.example.app.common.ComposeViewModel
import com.example.app.common.ResourceProvider
import com.example.app.domain.SessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
  private val sessionUseCase: SessionUseCase,
  private val resourceProvider: ResourceProvider,
) : ComposeViewModel<LoginState, LoginEvent>() {
  private var username by mutableStateOf("")
  private var password by mutableStateOf("")

  @Composable
  override fun uiState(): LoginState {
    return LoginState(
      username = username,
      password = password,
    )
  }

  override fun onEvent(event: LoginEvent) {
    when (event) {
      is LoginEvent.UsernameChange -> handleTypeUsername(event)
      is LoginEvent.PasswordChange -> handleTypePassword(event)
      is LoginEvent.LoginClick -> handleLoginClick(event)
    }
  }

  private fun handleTypeUsername(event: LoginEvent.UsernameChange) {
    username = event.username
  }

  private fun handleTypePassword(event: LoginEvent.PasswordChange) {
    password = event.password
  }

  private fun handleLoginClick(event: LoginEvent.LoginClick) {
    viewModelScope.launch {
      sessionUseCase.login(
        username = event.username,
        password = event.password,
      )
    }
  }

  private fun validateLoginFields(): Either<String, ValidInput> {
    return either {
      ensure(username.isNotBlank()) {
        resourceProvider.getString(R.string.login_error_blank_username)
      }
      ensure(password.isNotEmpty()) {
        resourceProvider.getString(R.string.login_error_empty_password)
      }

      ValidInput(
        username = username.trim(),
        password = password,
      )
    }
  }

  private data class ValidInput(
    val username: String,
    val password: String,
  )
}