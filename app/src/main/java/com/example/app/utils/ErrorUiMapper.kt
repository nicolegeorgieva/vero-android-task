package com.example.app.utils

import com.example.app.R
import com.example.app.common.ResourceProvider
import com.example.app.data.ErrorResponse
import javax.inject.Inject

class ErrorUiMapper @Inject constructor(
  private val resourceProvider: ResourceProvider,
) {
  fun map(error: ErrorResponse): String {
    return when (error) {
      ErrorResponse.NoInternet -> resourceProvider.getString(R.string.error_message_no_internet)
      ErrorResponse.Other -> resourceProvider.getString(R.string.error_message_other)
    }
  }
}