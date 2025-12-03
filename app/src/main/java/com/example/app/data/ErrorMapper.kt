package com.example.app.data

import java.net.UnknownHostException
import javax.inject.Inject

class ErrorMapper @Inject constructor() {
  fun map(error: Throwable): ErrorResponse {
    return when (error) {
      is UnknownHostException -> ErrorResponse.NoInternet
      else -> ErrorResponse.Other
    }
  }
}

sealed interface ErrorResponse {
  data object NoInternet : ErrorResponse
  data object Other : ErrorResponse
}