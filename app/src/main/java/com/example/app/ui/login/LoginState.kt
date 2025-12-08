package com.example.app.ui.login

import androidx.compose.runtime.Immutable

@Immutable
data class LoginState(
  val username: String,
  val password: String,
  val usernameError: String?,
  val passwordError: String?,
  val loginButtonLoading: Boolean,
  val serverErrorMessage: String?,
)