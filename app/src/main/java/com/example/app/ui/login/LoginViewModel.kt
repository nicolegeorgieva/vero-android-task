package com.example.app.ui.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import arrow.core.raise.either
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
  private var serverErrorMessage by mutableStateOf("")

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
      is LoginEvent.LoginClick -> handleLoginClick()
    }
  }

  private fun handleTypeUsername(event: LoginEvent.UsernameChange) {
    username = event.username
  }

  private fun handleTypePassword(event: LoginEvent.PasswordChange) {
    password = event.password
  }

  private fun handleLoginClick() {
    viewModelScope.launch {
      either {
        val validInput = validateLoginFields()
          .onLeft { errors ->
            errors.forEach { error ->
              when (error) {
                is InputError.UsernameError -> usernameErrorMessage = error.message
                is InputError.PasswordError -> passwordErrorMessage = error.message
              }
            }
          }.bind()

        sessionUseCase.login(
          username = validInput.username,
          password = validInput.password,
        ).onLeft { error ->
          serverErrorMessage = when (error) {
            LoginError.IncorrectCredentials -> resourceProvider.getString(
              R.string.login_error_incorrect_credentials
            )

            is LoginError.Other -> errorUiMapper.map(error.error)
          }
        }.bind()

        navigator.navigateTo(Screen.Home)
      }
    }
  }

  private fun validateLoginFields(): Either<List<InputError>, ValidInput> {
    val errors = mutableListOf<InputError>()

    if (username.isBlank()) {
      errors.add(InputError.UsernameError(resourceProvider.getString(R.string.login_error_blank_username)))
    }
    if (password.isEmpty()) {
      errors.add(InputError.PasswordError(resourceProvider.getString(R.string.login_error_empty_password)))
    }

    if (errors.isNotEmpty()) {
      return Either.Left(errors)
    }

    return ValidInput(
      username = username.trim(),
      password = password,
    ).right()
  }

  private sealed interface InputError {
    data class UsernameError(val message: String) : InputError
    data class PasswordError(val message: String) : InputError
  }

  private data class ValidInput(
    val username: String,
    val password: String,
  )
}