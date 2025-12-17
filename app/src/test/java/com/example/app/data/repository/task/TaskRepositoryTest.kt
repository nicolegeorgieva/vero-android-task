package com.example.app.data.repository.task

import app.cash.turbine.test
import arrow.core.Either
import com.example.app.data.ErrorMapper
import com.example.app.data.datasource.task.TaskDto
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

class TaskRepositoryTest {
  private val taskMapper = TaskMapper()
  private val localDataSource = mockk<TaskLocalDataSource>()
  private val remoteDataSource = mockk<TaskRemoteDataSource>()

  private lateinit var repository: TaskRepository

  private val taskDto = TaskDto(
    task = "1",
    title = "Buy materials",
    description = "From local market",
    colorCode = ""
  )
  private val task = taskMapper.dtoToDomain(taskDto)
  private val taskEntity = taskMapper.domainToEntity(task)

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
  fun `Tasks from DB and successful fetch of tasks from server`() = runTest {
    // given
    coEvery {
      localDataSource.getAllTasks()
    } returns listOf(taskEntity)
    coEvery {
      remoteDataSource.fetchTasks()
    } returns Either.Right(listOf(taskDto))
    coEvery {
      localDataSource.insertAllTasks(task = listOf(taskEntity))
    } just runs

    // when
    repository.getTasks(this).test {
      // then
      val first = awaitItem()
      expectThat(first).isNotNull().isRight(listOf(task))

      val second = awaitItem()
      expectThat(second).isNotNull().isRight(listOf(task))
    }
  }
}