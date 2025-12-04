package com.example.app.ui.home

import androidx.compose.ui.graphics.Color
import arrow.core.Either
import com.example.app.data.ErrorResponse
import com.example.app.domain.model.Task
import com.example.app.utils.ErrorUiMapper
import kotlinx.collections.immutable.toImmutableList
import javax.inject.Inject

class HomeUiMapper @Inject constructor(
  private val errorUiMapper: ErrorUiMapper,
) {
  fun map(tasksResponse: Either<ErrorResponse, List<Task>>?): HomeState {
    return tasksResponse?.fold(
      ifLeft = { error ->
        HomeState.Error(errorUiMapper.map(error))
      },
      ifRight = { tasks ->
        HomeState.Success(
          tasks = tasks.map { task ->
            TaskUi(
              task = task.task,
              title = task.title,
              description = task.description,
              color = when (val color = mapColorHex(task.colorHex)) {
                is Either.Left<Unit> -> Color.White
                is Either.Right<Color> -> color.value
              }
            )
          }.toImmutableList()
        )
      }
    ) ?: HomeState.Loading
  }
}

private fun mapColorHex(color: String): Either<Unit, Color> {
  return Either.catch {
    Color(color.removePrefix("#").toLong(16))
  }.mapLeft { Unit }
}