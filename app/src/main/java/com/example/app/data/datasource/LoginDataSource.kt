package com.example.app.data.datasource

import arrow.core.Either
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import javax.inject.Inject

class LoginDataSource @Inject constructor(
  private val httpClient: HttpClient,
) {
  suspend fun login(
    username: String,
    password: String
  ): Either<Throwable, SessionDto> {
    return Either.catch {
      httpClient.post("index.php/login") {
        header(key = "Authorization", value = "Basic QVBJX0V4cGxvcmVyOjEyMzQ1NmlzQUxhbWVQYXNz")
        setBody(LoginRequestDto(username = username, password = password))
      }.body<SessionDto>()
    }
  }
}

@Serializable
data class LoginRequestDto(
  val username: String,
  val password: String,
)

@Serializable
data class SessionDto(
  val oauth: OauthDto,
)

@Serializable
data class OauthDto(
  @SerialName("access_token")
  val accessToken: String,
  @SerialName("expires_in")
  val expiresIn: Long,
  @SerialName("token_type")
  val tokenType: String,
  @SerialName("refresh_token")
  val refreshToken: String,
)