package com.example.app.domain

import com.example.app.fixtures.TASK_1
import com.example.app.fixtures.TASK_2
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.isEmpty

class SearchUseCaseTest {
  private val searchUseCase = SearchUseCase()

  @Test
  fun `Show all tasks with empty search query`() {
    // when
    val tasks = searchUseCase.search(
      tasks = listOf(TASK_1, TASK_2),
      query = ""
    )
    // then
    expectThat(tasks).containsExactly(TASK_1, TASK_2)
  }

  @Test
  fun `Show no tasks when query is not matched`() {
    // when
    val tasks = searchUseCase.search(
      tasks = listOf(TASK_1, TASK_2),
      query = "3"
    )
    // then
    expectThat(tasks).isEmpty()
  }

  // region Query match
  @Test
  fun `Two match a query`() {
    // when
    val tasks = searchUseCase.search(
      tasks = listOf(TASK_1, TASK_2),
      query = "Bu"
    )
    // then
    expectThat(tasks).containsExactly(TASK_1, TASK_2)
  }

  @Test
  fun `Match task id`() {
    // when
    val tasks = searchUseCase.search(
      tasks = listOf(TASK_1, TASK_2),
      query = "2"
    )
    // then
    expectThat(tasks).containsExactly(TASK_2)
  }

  @Test
  fun `Match task title`() {
    // when
    val tasks = searchUseCase.search(
      tasks = listOf(TASK_1, TASK_2),
      query = "materials"
    )
    // then
    expectThat(tasks).containsExactly(TASK_1)
  }

  @Test
  fun `Match task description`() {
    // when
    val tasks = searchUseCase.search(
      tasks = listOf(TASK_1, TASK_2),
      query = "om"
    )
    // then
    expectThat(tasks).containsExactly(TASK_1)
  }

  @Test
  fun `Match task color hex`() {
    // when
    val task2 = TASK_2.copy(colorHex = "#FFFFFF")
    val tasks = searchUseCase.search(
      tasks = listOf(TASK_1, task2),
      query = "#FFFFFF"
    )
    // then
    expectThat(tasks).containsExactly(task2)
  }
  // endregion
}