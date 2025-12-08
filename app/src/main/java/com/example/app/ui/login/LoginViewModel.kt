package com.example.app.ui.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import arrow.core.right
import com.example.app.R
import com.example.app.common.ComposeViewModel
import com.example.app.common.ResourceProvider
import com.example.app.data.repository.login.LoginError
import com.example.app.domain.SessionUseCase
import com.example.app.navigation.Navigator
import com.example.app.navigation.Screen
import com.example.app.utils.ErrorUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
  private val sessionUseCase: SessionUseCase,
  private val resourceProvider: ResourceProvider,
  private val errorUiMapper: ErrorUiMapper,
  private val navigator: Navigator,
) : ComposeViewModel<LoginState, LoginEvent>() {
  private var username by mutableStateOf("")
  private var usernameErrorMessage by mutableStateOf<String?>(null)
  private var password by mutableStateOf("")
  private var passwordErrorMessage by mutableStateOf<String?>(null)
  private var loginButtonLoading by mutableStateOf(false)
  private var serverErrorMessage by mutableStateOf<String?>(null)

  @Composable
  override fun uiState(): LoginState {
    return LoginState(
      username = username,
      password = password,
      usernameError = usernameErrorMessage,
      passwordError = passwordErrorMessage,
      loginButtonLoading = loginButtonLoading,
      serverErrorMessage = serverErrorMessage,
    )
  }

  override fun onEvent(event: LoginEvent) {
    when (event) {
      is LoginEvent.ChangeUsername -> handleTypeUsername(event)
      is LoginEvent.ChangePassword -> handleTypePassword(event)
      is LoginEvent.LoginClick -> handleLoginClick()
    }
  }

  private fun handleTypeUsername(event: LoginEvent.ChangeUsername) {
    username = event.username
    if (usernameErrorMessage != null) {
      validateUsername()
    }
  }

  private fun handleTypePassword(event: LoginEvent.ChangePassword) {
    password = event.password
    if (passwordErrorMessage != null) {
      validatePassword()
    }
  }

  private fun handleLoginClick() {
    validateLoginFields().onRight {
      viewModelScope.launch {
        loginButtonLoading = true
        performLogin(it)
        loginButtonLoading = false
      }
    }
  }

  private fun validateUsername() {
    usernameErrorMessage = if (username.isBlank()) {
      resourceProvider.getString(R.string.login_error_blank_username)
    } else {
      null
    }
  }

  private fun validatePassword() {
    passwordErrorMessage = if (password.isEmpty()) {
      resourceProvider.getString(R.string.login_error_empty_password)
    } else {
      null
    }
  }

  private fun validateLoginFields(): Either<Unit, ValidInput> {
    var hasError = false

    if (username.isBlank()) {
      usernameErrorMessage = resourceProvider.getString(R.string.login_error_blank_username)
      hasError = true
    }
    if (password.isEmpty()) {
      passwordErrorMessage = resourceProvider.getString(R.string.login_error_empty_password)
      hasError = true
    }

    if (hasError) {
      return Either.Left(Unit)
    }

    return ValidInput(
      username = username.trim(),
      password = password,
    ).right()
  }

  private data class ValidInput(
    val username: String,
    val password: String,
  )

  private suspend fun performLogin(input: ValidInput) {
    sessionUseCase.login(
      username = input.username,
      password = input.password,
    ).onLeft { error ->
      serverErrorMessage = when (error) {
        LoginError.IncorrectCredentials -> resourceProvider.getString(
          R.string.login_error_incorrect_credentials
        )

        is LoginError.Other -> errorUiMapper.map(error.error)
      }
    }.onRight {
      navigator.navigateTo(Screen.Home)
    }
  }
}