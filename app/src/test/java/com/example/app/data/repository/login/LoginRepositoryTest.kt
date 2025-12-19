package com.example.app.data.repository.login

import arrow.core.Either
import com.example.app.data.ErrorMapper
import com.example.app.data.ErrorResponse
import com.example.app.data.datasource.login.IncorrectCredentialsException
import com.example.app.data.datasource.login.LoginDataSource
import com.example.app.data.datasource.login.OauthDto
import com.example.app.data.datasource.login.SessionDto
import com.example.app.domain.model.Session
import com.example.app.fixtures.ACCESS_TOKEN_1
import com.example.app.fixtures.CORRECT_CREDENTIALS
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import java.net.UnknownHostException

class LoginRepositoryTest {
  private val dataSource = mockk<LoginDataSource>()
  private val repository = LoginRepository(
    dataSource = dataSource,
    errorMapper = LoginErrorMapper(errorMapper = ErrorMapper())
  )

  @Test
  fun `Successful login with mapped SessionDto to Session domain`() = runTest {
    // given
    coEvery {
      dataSource.login(
        username = CORRECT_CREDENTIALS.username,
        password = CORRECT_CREDENTIALS.password,
      )
    } returns Either.Right(
      SessionDto(oauth = OauthDto(accessToken = ACCESS_TOKEN_1))
    )

    // when
    val res = repository.login(
      username = CORRECT_CREDENTIALS.username,
      password = CORRECT_CREDENTIALS.password,
    )

    // then
    expectThat(res).isEqualTo(
      Either.Right(
        Session(accessToken = ACCESS_TOKEN_1)
      )
    )
  }

  @Test
  fun `No internet error should be mapped to ErrorResponse-NoInternet`() = runTest {
    // given
    coEvery {
      dataSource.login(
        username = CORRECT_CREDENTIALS.username,
        password = CORRECT_CREDENTIALS.password,
      )
    } returns Either.Left(UnknownHostException())

    // when
    val res = repository.login(
      username = CORRECT_CREDENTIALS.username,
      password = CORRECT_CREDENTIALS.password,
    )

    // then
    expectThat(res).isEqualTo(
      Either.Left(LoginError.Other(ErrorResponse.NoInternet))
    )
  }

  @Test
  fun `Wrong credentials should lead to LoginError-IncorrectCredentials`() = runTest {
    // given
    val wrongCredentials = CORRECT_CREDENTIALS.copy(
      username = "${CORRECT_CREDENTIALS.username}1",
      password = CORRECT_CREDENTIALS.password
    )
    coEvery {
      dataSource.login(
        username = wrongCredentials.username,
        password = wrongCredentials.password,
      )
    } returns Either.Left(IncorrectCredentialsException())

    // when
    val res = repository.login(
      username = wrongCredentials.username,
      password = wrongCredentials.password,
    )

    // then
    expectThat(res).isEqualTo(
      Either.Left(LoginError.IncorrectCredentials)
    )
  }
}