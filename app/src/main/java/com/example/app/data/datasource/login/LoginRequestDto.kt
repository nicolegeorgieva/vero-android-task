package com.example.app.data.datasource.login

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDto(
  val username: String,
  val password: String,
)