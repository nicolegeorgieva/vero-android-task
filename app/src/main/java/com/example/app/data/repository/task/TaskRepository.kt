package com.example.app.data.repository.task

import arrow.core.Either
import com.example.app.data.ErrorMapper
import com.example.app.data.ErrorResponse
import com.example.app.data.datasource.task.TaskDataSource
import com.example.app.domain.model.Task
import javax.inject.Inject

class TaskRepository @Inject constructor(
  private val dataSource: TaskDataSource,
  private val errorMapper: ErrorMapper,
) {
  suspend fun getTasks(): Either<ErrorResponse, List<Task>> {
    return dataSource.fetchTasks()
      .mapLeft(errorMapper::map)
      .map { tasks ->
        tasks.map { taskDto ->
          Task(
            id = taskDto.task,
            title = taskDto.title,
            description = taskDto.description,
            colorHex = taskDto.colorCode,
          )
        }
      }
  }
}