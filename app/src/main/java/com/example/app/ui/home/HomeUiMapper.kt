package com.example.app.ui.home

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import arrow.core.Either
import com.example.app.data.ErrorResponse
import com.example.app.domain.model.Task
import com.example.app.ui.model.Loadable
import com.example.app.utils.ErrorUiMapper
import kotlinx.collections.immutable.toImmutableList
import javax.inject.Inject

class HomeUiMapper @Inject constructor(
  private val errorUiMapper: ErrorUiMapper,
) {
  fun map(
    searchText: String,
    tasksResponse: Either<ErrorResponse, List<Task>>?,
    isRefreshing: Boolean,
  ): HomeState {
    return tasksResponse?.fold(
      ifLeft = { error ->
        HomeState.Error(
          message = errorUiMapper.map(error),
          searchText = searchText,
        )
      },
      ifRight = { tasks ->
        HomeState.Content(
          tasks = Loadable.Content(
            tasks.filter { task ->
              task.id.contains(other = searchText, ignoreCase = true) ||
                  task.title.contains(other = searchText, ignoreCase = true) ||
                  task.description.contains(other = searchText, ignoreCase = true) ||
                  task.colorHex.contains(other = searchText, ignoreCase = true)
            }.map { task ->
              TaskUi(
                id = task.id,
                title = task.title,
                description = task.description,
                color = mapColorHex(task.colorHex).getOrNull()
              )
            }.toImmutableList()
          ),
          isRefreshing = isRefreshing,
          searchText = searchText,
        )
      }
    ) ?: HomeState.Content(
      tasks = Loadable.Loading,
      isRefreshing = isRefreshing,
      searchText = searchText,
    )
  }
}

private fun mapColorHex(color: String): Either<Throwable, Color> {
  return Either.catch {
    Color(color.toColorInt())
  }
}