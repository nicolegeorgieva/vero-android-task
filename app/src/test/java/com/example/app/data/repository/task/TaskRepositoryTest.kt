package com.example.app.data.repository.task

import app.cash.turbine.test
import arrow.core.Either
import com.example.app.data.ErrorMapper
import com.example.app.data.datasource.task.TaskLocalDataSource
import com.example.app.data.datasource.task.TaskRemoteDataSource
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.arrow.isRight
import strikt.assertions.isNotNull
import java.net.UnknownHostException

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

  @Test
  fun `Tasks from DB & successful fetch of tasks from server`() = runTest {
    // given
    coEvery {
      localDataSource.getAllTasks()
    } returns listOf(TASK_1_ENTITY)
    coEvery {
      remoteDataSource.fetchTasks()
    } returns Either.Right(listOf(TASK_1_DTO, TASK_2_DTO))
    coEvery {
      localDataSource.insertAllTasks(task = listOf(TASK_1_ENTITY, TASK_2_ENTITY))
    } just runs

    // when
    repository.getTasks(this).test {
      // then
      val first = awaitItem()
      expectThat(first).isNotNull().isRight(listOf(TASK_1))

      val second = awaitItem()
      expectThat(second).isNotNull().isRight(listOf(TASK_1, TASK_2))
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

    // when
    repository.getTasks(this).test {
      // then
      val data = awaitItem()
      expectThat(data).isNotNull().isRight(listOf(TASK_1))
    }
  }
}