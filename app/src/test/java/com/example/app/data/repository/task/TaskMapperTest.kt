package com.example.app.data.repository.task

import com.example.app.data.database.task.TaskEntity
import com.example.app.data.datasource.task.TaskDto
import com.example.app.domain.model.Task
import com.example.app.fixtures.TASK_1_DTO
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class TaskMapperTest {
  private val taskMapper = TaskMapper()

  // region entityToDomain()
  @Test
  fun `Entity to domain`() {
    // Given
    val entity = TaskEntity(
      id = "1",
      title = "Buy materials",
      description = "From local market",
      colorCode = null
    )
    // when
    val task = taskMapper.entityToDomain(entity)
    // then
    expectThat(task).isEqualTo(
      Task(
        id = "1",
        title = "Buy materials",
        description = "From local market",
        colorHex = null
      )
    )
  }
  // endregion

  // region dtoToDomain()
  @Test
  fun `DTO to domain with empty colorCode`() {
    // given
    val dto = TaskDto(
      task = "1",
      title = "Buy materials",
      description = "From local market",
      colorCode = ""
    )
    // when
    val task = taskMapper.dtoToDomain(dto)
    // then
    expectThat(task).isEqualTo(
      Task(
        id = "1",
        title = "Buy materials",
        description = "From local market",
        colorHex = null
      )
    )
  }

  @Test
  fun `DTO to domain with colorCode`() {
    // given
    val dto = TASK_1_DTO.copy(colorCode = "#FFFFFF")
    // when
    val task = taskMapper.dtoToDomain(dto)
    // then
    expectThat(task.colorHex).isEqualTo("#FFFFFF")
  }
  // endregion

  // region domainToEntity()
  @Test
  fun `Domain to entity`() {
    // given
    val task = Task(
      id = "1",
      title = "Buy materials",
      description = "From local market",
      colorHex = null
    )
    // when
    val entity = taskMapper.domainToEntity(task)
    // then
    expectThat(entity).isEqualTo(
      TaskEntity(
        id = "1",
        title = "Buy materials",
        description = "From local market",
        colorCode = null
      )
    )
  }
  // endregion
}