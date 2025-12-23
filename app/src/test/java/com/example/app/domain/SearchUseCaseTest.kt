package com.example.app.domain

import com.example.app.domain.model.Task
import com.example.app.fixtures.EMPTY_TASK
import com.example.app.fixtures.TASK_1
import com.example.app.fixtures.TASK_2
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import org.junit.Test
import org.junit.runner.RunWith
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.isEmpty

@RunWith(TestParameterInjector::class)
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

  enum class SearchTestCase(
    val query: String,
    val task: Task,
    val shouldMatch: Boolean
  ) {
    // region id
    PARTIAL_ID_MATCH(
      query = "TASK-",
      task = EMPTY_TASK.copy(id = "TASK-13"),
      shouldMatch = true,
    ),
    EXACT_ID_MATCH(
      query = "TASK-13",
      task = EMPTY_TASK.copy(id = "TASK-13"),
      shouldMatch = true,
    ),
    NO_ID_MATCH(
      query = "TASK-14",
      task = EMPTY_TASK.copy(id = "TASK-13"),
      shouldMatch = false,
    ),
    // endregion

    // region title
    PARTIAL_TITLE_MATCH(
      query = "y",
      task = EMPTY_TASK.copy(title = "Buy materials"),
      shouldMatch = true,
    ),
    EXACT_TITLE_MATCH(
      query = "Buy materials",
      task = EMPTY_TASK.copy(title = "Buy materials"),
      shouldMatch = true,
    ),
    NO_TITLE_MATCH(
      query = "Bui",
      task = EMPTY_TASK.copy(title = "Buy materials"),
      shouldMatch = false,
    ),
    // endregion

    // region description
    PARTIAL_DESCRIPTION_MATCH(
      query = "ket",
      task = EMPTY_TASK.copy(description = "From local market"),
      shouldMatch = true,
    ),
    EXACT_DESCRIPTION_MATCH(
      query = "From local market",
      task = EMPTY_TASK.copy(description = "From local market"),
      shouldMatch = true,
    ),
    NO_DESCRIPTION_MATCH(
      query = "marketing",
      task = EMPTY_TASK.copy(description = "From local market"),
      shouldMatch = false,
    ),
    // endregion

    // region color hex
    PARTIAL_COLOR_MATCH(
      query = "#",
      task = EMPTY_TASK.copy(colorHex = "#FFFFFF"),
      shouldMatch = true,
    ),
    EXACT_COLOR_MATCH(
      query = "#FFFFFF",
      task = EMPTY_TASK.copy(colorHex = "#FFFFFF"),
      shouldMatch = true,
    ),
    NO_COLOR_MATCH(
      query = "#0000FF",
      task = EMPTY_TASK.copy(colorHex = "#FFFFFF"),
      shouldMatch = false,
    ),
    // endregion
  }

  @Test
  fun `search criteria`(@TestParameter testCase: SearchTestCase) {
    // When
    val res = searchUseCase.search(listOf(testCase.task), testCase.query)

    // Then
    if (testCase.shouldMatch) {
      expectThat(res).containsExactly(testCase.task)
    } else {
      expectThat(res).isEmpty()
    }
  }
}