package com.example.app.data.datastore

import com.example.app.fixtures.ACCESS_TOKEN_1
import com.example.app.fixtures.SESSION_1
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNull

class SessionStorageTest {
  private val dataStore = TestDataStore()
  private val sessionStorage = SessionStorage(dataStore = dataStore)

  @Before
  fun setup() {
    dataStore.clear()
  }

  @Test
  fun `There is no session`() = runTest {
    // when
    val session = sessionStorage.get()
    // then
    expectThat(session).isNull()
  }

  @Test
  fun `There is session`() = runTest {
    // given
    dataStore[DataStoreKeys.accessToken] = ACCESS_TOKEN_1
    // when
    val session = sessionStorage.get()
    // then
    expectThat(session).isEqualTo(SESSION_1)
    expectThat(dataStore[DataStoreKeys.accessToken]).isEqualTo(ACCESS_TOKEN_1)
  }

  @Test
  fun `Save session`() = runTest {
    // when
    sessionStorage.store(SESSION_1)
    val session = sessionStorage.get()
    // then
    expectThat(session).isEqualTo(SESSION_1)
    expectThat(dataStore[DataStoreKeys.accessToken]).isEqualTo(ACCESS_TOKEN_1)
  }

  @Test
  fun `Remove access token`() = runTest {
    // given
    dataStore[DataStoreKeys.accessToken] = ACCESS_TOKEN_1
    // when
    sessionStorage.remove()
    val session = sessionStorage.get()
    // then
    expectThat(session).isNull()
    expectThat(dataStore[DataStoreKeys.accessToken]).isNull()
  }
}