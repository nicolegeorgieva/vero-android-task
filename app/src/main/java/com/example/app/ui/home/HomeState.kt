package com.example.app.ui.home

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import kotlinx.collections.immutable.ImmutableList

@Immutable
sealed interface HomeState {
  data object Loading : HomeState
  data class Error(val message: String) : HomeState
  data class Success(val tasks: ImmutableList<TaskUi>) : HomeState
}

@Immutable
data class TaskUi(
  val task: String,
  val title: String,
  val description: String,
  val color: Color?,
)