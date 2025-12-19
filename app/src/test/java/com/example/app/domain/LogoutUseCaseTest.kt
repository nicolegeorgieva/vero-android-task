package com.example.app.domain

import com.example.app.data.datasource.task.TaskLocalDataSource
import com.example.app.data.datastore.SessionStorage
import com.example.app.data.datastore.TestDataStore
import com.example.app.fixtures.SESSION_1
import com.example.app.worker.BackgroundWorkerManager
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isNull

class LogoutUseCaseTest {
  private val dataStore = TestDataStore()
  private val sessionStorage = SessionStorage(dataStore = dataStore)
  private val taskDataSource = mockk<TaskLocalDataSource>()
  private val workerManager = mockk<BackgroundWorkerManager>()
  private val logoutUseCase = LogoutUseCase(
    sessionStorage = sessionStorage,
    taskDataSource = taskDataSource,
    workerManager = workerManager
  )

  @Before
  fun setup() {
    dataStore.clear()
  }

  @Test
  fun `Log out`() = runTest {
    // given
    sessionStorage.store(SESSION_1)
    coEvery { workerManager.cancel() } just runs
    coEvery { taskDataSource.deleteTasks() } just runs
    // when
    logoutUseCase.logout()
    // then
    expectThat(sessionStorage.get()).isNull()
    coVerify(exactly = 1) {
      taskDataSource.deleteTasks()
      workerManager.cancel()
    }
  }
}