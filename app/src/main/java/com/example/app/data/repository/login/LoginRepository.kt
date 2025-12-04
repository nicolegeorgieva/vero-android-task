package com.example.app.data.repository.login

import arrow.core.Either
import com.example.app.data.datasource.login.LoginDataSource
import com.example.app.domain.model.Session
import javax.inject.Inject

class LoginRepository @Inject constructor(
  private val dataSource: LoginDataSource,
  private val errorMapper: LoginErrorMapper,
) {
  suspend fun login(
    username: String,
    password: String
  ): Either<LoginError, Session> {
    return dataSource.login(
      username = username,
      password = password
    ).mapLeft(errorMapper::map)
      .map { sessionDto ->
        Session(accessToken = sessionDto.oauth.accessToken)
      }
  }
}