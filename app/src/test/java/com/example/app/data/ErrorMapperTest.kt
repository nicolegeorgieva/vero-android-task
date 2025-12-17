package com.example.app.data

import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import java.net.UnknownHostException

class ErrorMapperTest {
  private val errorMapper = ErrorMapper()

  @Test
  fun `Map no internet error`() = runTest {
    // when
    val error = errorMapper.map(UnknownHostException())
    // then
    expectThat(error).isEqualTo(ErrorResponse.NoInternet)
  }

  @Test
  fun `Map server error`() = runTest {
    // when
    val error = errorMapper.map(Exception("error"))
    // then
    expectThat(error).isEqualTo(ErrorResponse.Other)
  }
}