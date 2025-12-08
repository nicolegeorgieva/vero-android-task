package com.example.app.ui.login

sealed interface LoginEvent {
  data class UsernameChange(val username: String) : LoginEvent
  data class PasswordChange(val password: String) : LoginEvent
  data class LoginClick(
    val username: String,
    val password: String
  ) : LoginEvent
}