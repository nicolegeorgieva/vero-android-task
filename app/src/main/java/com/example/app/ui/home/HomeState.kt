package com.example.app.ui.home

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.example.app.ui.model.Loadable
import kotlinx.collections.immutable.ImmutableList

@Immutable
sealed interface HomeState {
  val searchQuery: String

  data class Content(
    override val searchQuery: String,
    val tasks: Loadable<ImmutableList<TaskUi>>,
    val isRefreshing: Boolean,
  ) : HomeState

  data class Error(
    override val searchQuery: String,
    val message: String
  ) : HomeState
}

@Immutable
data class TaskUi(
  val id: String,
  val title: String,
  val description: String,
  val color: Color?,
)