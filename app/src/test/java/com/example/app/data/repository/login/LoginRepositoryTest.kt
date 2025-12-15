package com.example.app.data.repository.login

import arrow.core.Either
import com.example.app.data.ErrorMapper
import com.example.app.data.datasource.login.LoginDataSource
import com.example.app.data.datasource.login.OauthDto
import com.example.app.data.datasource.login.SessionDto
import com.example.app.domain.model.Session
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class LoginRepositoryTest {
  private lateinit var repository: LoginRepository
  private val dataSource = mockk<LoginDataSource>()

  private val rightCredentials = Credentials(
    username = "test",
    password = "123"
  )
  private val accessToken = "J5672"

  private data class Credentials(
    val username: String,
    val password: String,
  )


  @Before
  fun setup() {
    repository = LoginRepository(
      dataSource = dataSource,
      errorMapper = LoginErrorMapper(errorMapper = ErrorMapper())
    )
  }

  @Test
  fun `Successful login with mapped SessionDto to Session domain`() = runTest {
    // given
    coEvery {
      dataSource.login(
        username = rightCredentials.username,
        password = rightCredentials.password,
      )
    } returns Either.Right(
      SessionDto(oauth = OauthDto(accessToken = accessToken))
    )

    // when
    val res = repository.login(
      username = rightCredentials.username,
      password = rightCredentials.password,
    )

    // then
    expectThat(res).isEqualTo(
      Either.Right(
        Session(
          accessToken = accessToken
        )
      )
    )
  }

  @Test
  fun `No internet error should be mapped to ErrorResponse-NoInternet`() {
    TODO()
  }

  @Test
  fun `Wrong credentials should lead to LoginError-IncorrectCredentials`() {
    TODO()
  }
}