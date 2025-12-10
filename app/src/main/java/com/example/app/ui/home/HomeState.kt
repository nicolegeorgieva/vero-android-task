package com.example.app.ui.home

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.example.app.ui.model.Loadable
import kotlinx.collections.immutable.ImmutableList

@Immutable
sealed interface HomeState {
  data class Content(val tasks: Loadable<ImmutableList<TaskUi>>) : HomeState
  data class Error(val message: String) : HomeState
}

@Immutable
data class TaskUi(
  val id: String,
  val title: String,
  val description: String,
  val color: Color?,
)