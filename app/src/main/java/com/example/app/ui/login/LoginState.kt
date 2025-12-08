package com.example.app.ui.login

import androidx.compose.runtime.Immutable

@Immutable
data class LoginState(
  val username: String,
  val password: String,
  val loginButtonLoading: Boolean,
)