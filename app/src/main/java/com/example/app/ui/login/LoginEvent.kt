package com.example.app.ui.login

sealed interface LoginEvent {
  data class ChangeUsername(val username: String) : LoginEvent
  data class ChangePassword(val password: String) : LoginEvent
  data object LoginClick : LoginEvent
}