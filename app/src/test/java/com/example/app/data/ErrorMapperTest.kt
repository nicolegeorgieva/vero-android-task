package com.example.app.data

import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import java.net.UnknownHostException

@RunWith(TestParameterInjector::class)
class ErrorMapperTest {
  private val errorMapper = ErrorMapper()

  enum class ErrorMappingTestCase(
    val error: Throwable,
    val expected: ErrorResponse,
  ) {
    NO_INTERNET(
      error = UnknownHostException(),
      expected = ErrorResponse.NoInternet
    ),
    OTHER(
      error = Exception("error"),
      expected = ErrorResponse.Other
    )
  }

  @Test
  fun `Error mapping`(
    @TestParameter testCase: ErrorMappingTestCase,
  ) = runTest {
    // when
    val error = errorMapper.map(testCase.error)
    // then
    expectThat(error).isEqualTo(testCase.expected)
  }
}