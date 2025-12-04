package com.example.app.data.datasource.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SessionDto(
  val oauth: OauthDto,
)

@Serializable
data class OauthDto(
  @SerialName("access_token")
  val accessToken: String,
)