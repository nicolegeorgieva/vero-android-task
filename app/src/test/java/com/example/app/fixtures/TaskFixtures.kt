package com.example.app.fixtures

import com.example.app.data.datasource.task.TaskDto
import com.example.app.data.repository.task.TaskMapper
import com.example.app.domain.model.Task

private val taskMapper = TaskMapper()

val EMPTY_TASK = Task(
  id = "",
  title = "",
  description = "",
  colorHex = ""
)

val TASK_1_DTO = TaskDto(
  task = "1",
  title = "Buy materials",
  description = "From local market",
  colorCode = ""
)
val TASK_1 = taskMapper.dtoToDomain(TASK_1_DTO)
val TASK_1_ENTITY = taskMapper.domainToEntity(TASK_1)

val TASK_2_DTO = TaskDto(
  task = "2",
  title = "Build the base",
  description = "",
  colorCode = ""
)
val TASK_2 = taskMapper.dtoToDomain(TASK_2_DTO)
val TASK_2_ENTITY = taskMapper.domainToEntity(TASK_2)