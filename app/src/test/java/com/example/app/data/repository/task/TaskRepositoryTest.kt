package com.example.app.data.repository.task

import app.cash.turbine.test
import arrow.core.Either
import com.example.app.data.ErrorMapper
import com.example.app.data.ErrorResponse
import com.example.app.data.datasource.task.TaskLocalDataSource
import com.example.app.data.datasource.task.TaskRemoteDataSource
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import strikt.api.expectThat
import strikt.arrow.isLeft
import strikt.arrow.isRight
import strikt.assertions.isNotNull
import strikt.assertions.isNull
import java.net.UnknownHostException

@RunWith(TestParameterInjector::class)
class TaskRepositoryTest {
  private val localDataSource = mockk<TaskLocalDataSource>()
  private val remoteDataSource = mockk<TaskRemoteDataSource>()

  private lateinit var repository: TaskRepository

  @Before
  fun setup() {
    repository = TaskRepository(
      localDataSource = localDataSource,
      remoteDataSource = remoteDataSource,
      taskMapper = TaskMapper(),
      errorMapper = ErrorMapper()
    )
  }

  // region getTasks()
  @Test
  fun `Tasks from DB & successful fetch of tasks from server`() = runTest {
    // given
    coEvery {
      localDataSource.getAllTasks()
    } returns listOf(TASK_1_ENTITY)
    coEvery {
      remoteDataSource.fetchTasks()
    } returns Either.Right(listOf(TASK_1_DTO, TASK_2_DTO))
    coEvery { localDataSource.insertAllTasks(any()) } just runs

    // when
    repository.getTasks(this).test {
      // then
      val first = awaitItem()
      expectThat(first).isNotNull().isRight(listOf(TASK_1))

      val second = awaitItem()
      expectThat(second).isNotNull().isRight(listOf(TASK_1, TASK_2))
      coVerify(exactly = 1) {
        localDataSource.insertAllTasks(listOf(TASK_1_ENTITY, TASK_2_ENTITY))
      }

      expectNoEvents()
    }
  }

  @Test
  fun `Tasks from DB & no internet`() = runTest {
    // given
    coEvery {
      localDataSource.getAllTasks()
    } returns listOf(TASK_1_ENTITY)
    coEvery {
      remoteDataSource.fetchTasks()
    } returns Either.Left(UnknownHostException())
    coEvery { localDataSource.insertAllTasks(any()) } just runs

    // when
    repository.getTasks(this).test {
      // then
      val data = awaitItem()
      expectThat(data).isNotNull().isRight(listOf(TASK_1))
      coVerify(exactly = 0) {
        localDataSource.insertAllTasks(any())
      }
      expectNoEvents()
    }
  }

  @Test
  fun `No tasks in DB & no internet`() = runTest {
    // given
    coEvery {
      localDataSource.getAllTasks()
    } returns listOf()
    coEvery {
      remoteDataSource.fetchTasks()
    } returns Either.Left(UnknownHostException())
    coEvery { localDataSource.insertAllTasks(any()) } just runs

    // when
    repository.getTasks(this).test {
      // then
      val data = awaitItem()
      expectThat(data).isNotNull().isLeft(ErrorResponse.NoInternet)
      coVerify(exactly = 0) {
        localDataSource.insertAllTasks(any())
      }
      expectNoEvents()
    }
  }

  @Test
  fun `Tasks in DB & server error`() = runTest {
    // given
    coEvery {
      localDataSource.getAllTasks()
    } returns listOf(TASK_1_ENTITY, TASK_2_ENTITY)
    coEvery {
      remoteDataSource.fetchTasks()
    } returns Either.Left(Exception("error"))
    coEvery { localDataSource.insertAllTasks(any()) } just runs

    // when
    repository.getTasks(this).test {
      // then
      val first = awaitItem()
      expectThat(first).isNotNull().isRight(listOf(TASK_1, TASK_2))

      val second = awaitItem()
      expectThat(second).isNotNull().isLeft(ErrorResponse.Other)
      coVerify(exactly = 0) {
        localDataSource.insertAllTasks(any())
      }
      expectNoEvents()
    }
  }
  // endregion

  // region refresh()
  @Test
  fun `Successful fetch from server`(
    @TestParameter showLoading: Boolean
  ) = runTest {
    // given
    coEvery {
      remoteDataSource.fetchTasks()
    } returns Either.Right(listOf(TASK_1_DTO))
    coEvery { localDataSource.insertAllTasks(any()) } just runs

    repository.tasksFlow.test {
      // when
      repository.refresh(showLoading = showLoading)
      // then
      if (showLoading) {
        val loading = awaitItem()
        expectThat(loading).isNull()
      }

      val data = awaitItem()
      expectThat(data).isNotNull().isRight(listOf(TASK_1))
      coVerify(exactly = 1) {
        localDataSource.insertAllTasks(listOf(TASK_1_ENTITY))
      }
      expectNoEvents()
    }
  }

  @Test
  fun `No internet`(
    @TestParameter showLoading: Boolean
  ) = runTest {
    // given
    coEvery {
      remoteDataSource.fetchTasks()
    } returns Either.Left(UnknownHostException())
    coEvery { localDataSource.insertAllTasks(any()) } just runs

    repository.tasksFlow.test {
      // when
      repository.refresh(showLoading = showLoading)
      // then
      if (showLoading) {
        val loading = awaitItem()
        expectThat(loading).isNull()
      }

      val data = awaitItem()
      expectThat(data).isNotNull().isLeft(ErrorResponse.NoInternet)
      coVerify(exactly = 0) {
        localDataSource.insertAllTasks(any())
      }
      expectNoEvents()
    }
  }
  // endregion
}