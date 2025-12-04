package com.example.app.data.repository.login

import com.example.app.data.ErrorResponse

sealed interface LoginError {
  data object IncorrectCredentials : LoginError
  data class Other(val error: ErrorResponse) : LoginError
}