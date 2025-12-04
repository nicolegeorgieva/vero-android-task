package com.example.app.data.datasource.login

import arrow.core.Either
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess
import javax.inject.Inject

class LoginDataSource @Inject constructor(
  private val httpClient: HttpClient,
) {
  suspend fun login(
    username: String,
    password: String
  ): Either<Throwable, SessionDto> {
    return Either.catch {
      val response = httpClient.post("index.php/login") {
        header(key = "Authorization", value = "Basic QVBJX0V4cGxvcmVyOjEyMzQ1NmlzQUxhbWVQYXNz")
        setBody(LoginRequestDto(username = username, password = password))
      }

      when {
        response.status.isSuccess() -> response.body<SessionDto>()
        response.status == HttpStatusCode.Unauthorized -> throw IncorrectCredentialsException()
        else -> throw UnexpectedException()
      }
    }
  }
}