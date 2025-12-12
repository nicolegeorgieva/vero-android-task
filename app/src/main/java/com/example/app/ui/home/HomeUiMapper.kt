package com.example.app.ui.home

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import arrow.core.Either
import com.example.app.data.ErrorResponse
import com.example.app.domain.SearchUseCase
import com.example.app.domain.model.Task
import com.example.app.ui.model.Loadable
import com.example.app.utils.ErrorUiMapper
import kotlinx.collections.immutable.toImmutableList
import javax.inject.Inject

class HomeUiMapper @Inject constructor(
  private val errorUiMapper: ErrorUiMapper,
  private val searchUseCase: SearchUseCase,
) {
  fun map(
    searchQuery: String,
    tasksResponse: Either<ErrorResponse, List<Task>>?,
    isRefreshing: Boolean,
  ): HomeState {
    return tasksResponse?.fold(
      ifLeft = { error ->
        HomeState.Error(
          message = errorUiMapper.map(error),
          searchQuery = searchQuery,
        )
      },
      ifRight = { tasks ->
        HomeState.Content(
          tasks = Loadable.Content(
            searchUseCase.search(
              tasks = tasks,
              query = searchQuery,
            ).map(::taskToUi)
              .toImmutableList()
          ),
          isRefreshing = isRefreshing,
          searchQuery = searchQuery,
        )
      }
    ) ?: HomeState.Content(
      tasks = Loadable.Loading,
      isRefreshing = isRefreshing,
      searchQuery = searchQuery,
    )
  }

  private fun taskToUi(task: Task): TaskUi {
    return TaskUi(
      id = task.id,
      title = task.title,
      description = task.description,
      color = task.colorHex?.mapColorHex()?.getOrNull(),
    )
  }

  private fun String.mapColorHex(): Either<Throwable, Color> {
    return Either.catch {
      Color(this.toColorInt())
    }
  }
}