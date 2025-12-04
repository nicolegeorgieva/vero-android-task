package com.example.app.data.repository.login

import com.example.app.data.ErrorMapper
import com.example.app.data.datasource.login.IncorrectCredentialsException
import javax.inject.Inject

class LoginErrorMapper @Inject constructor(
  private val errorMapper: ErrorMapper,
) {
  fun map(error: Throwable): LoginError {
    return when (error) {
      is IncorrectCredentialsException -> LoginError.IncorrectCredentials
      else -> LoginError.Other(errorMapper.map(error))
    }
  }
}