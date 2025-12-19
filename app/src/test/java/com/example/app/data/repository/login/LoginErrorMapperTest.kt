package com.example.app.data.repository.login

import com.example.app.data.ErrorMapper
import com.example.app.data.ErrorResponse
import com.example.app.data.datasource.login.IncorrectCredentialsException
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class LoginErrorMapperTest {
  private val loginErrorMapper = LoginErrorMapper(errorMapper = ErrorMapper())

  @Test
  fun `Map incorrect credentials`() {
    // when
    val error = loginErrorMapper.map(IncorrectCredentialsException())
    // then
    expectThat(error).isEqualTo(LoginError.IncorrectCredentials)
  }

  @Test
  fun `Map other exception`() {
    // when
    val error = loginErrorMapper.map(Exception())
    // then
    expectThat(error).isEqualTo(LoginError.Other(ErrorResponse.Other))
  }
}