package com.example.app.data.repository.task

import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class TaskMapperTest {
  private val taskMapper = TaskMapper()

  // region entityToDomain()
  @Test
  fun `Entity to domain`() {
    // when
    val task = taskMapper.entityToDomain(TASK_1_ENTITY)
    // then
    expectThat(task).isEqualTo(TASK_1)
  }
  // endregion

  // region dtoToDomain()
  @Test
  fun `DTO to domain`() {
    // when
    val task = taskMapper.dtoToDomain(TASK_1_DTO)
    // then
    expectThat(task).isEqualTo(TASK_1)
  }
  // endregion

  // region domainToEntity()
  @Test
  fun `Domain to entity`() {
    // when
    val entity = taskMapper.domainToEntity(TASK_1)
    // then
    expectThat(entity).isEqualTo(TASK_1_ENTITY)
  }
  // endregion
}