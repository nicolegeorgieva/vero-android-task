package com.example.app.ui.home

import androidx.compose.ui.graphics.Color
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
  fun map(tasksResponse: Either<ErrorResponse, List<Task>>?): HomeState {
    return tasksResponse?.fold(
      ifLeft = { error ->
        HomeState.Error(message = errorUiMapper.map(error))
      },
      ifRight = { tasks ->
        HomeState.Content(
          tasks = Loadable.Content(
            tasks.map { task ->
              TaskUi(
                id = task.id,
                title = task.title,
                description = task.description,
                color = mapColorHex(task.colorHex).getOrNull()
              )
            }.toImmutableList()
          )
        )
      }
    ) ?: HomeState.Content(tasks = Loadable.Loading)
  }
}

private fun mapColorHex(color: String): Either<Throwable, Color> {
  return Either.catch {
    Color(color.removePrefix("#").toLong(16))
  }
}