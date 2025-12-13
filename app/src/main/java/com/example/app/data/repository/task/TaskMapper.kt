package com.example.app.data.repository.task

import com.example.app.data.database.task.TaskEntity
import com.example.app.data.datasource.task.TaskDto
import com.example.app.domain.model.Task
import javax.inject.Inject

class TaskMapper @Inject constructor() {
  fun entityToDomain(task: TaskEntity): Task {
    return Task(
      id = task.id,
      title = task.title,
      description = task.description,
      colorHex = task.colorCode,
    )
  }

  fun dtoToDomain(task: TaskDto): Task {
    return Task(
      id = task.task,
      title = task.title,
      description = task.description.takeIf {
        it.isNotBlank()
      },
      colorHex = task.colorCode.takeIf {
        it.isNotBlank()
      },
    )
  }

  fun domainToEntity(task: Task): TaskEntity {
    return TaskEntity(
      id = task.id,
      title = task.title,
      description = task.description,
      colorCode = task.colorHex,
    )
  }
}