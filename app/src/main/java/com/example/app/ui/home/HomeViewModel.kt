package com.example.app.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import arrow.core.Either
import com.example.app.common.ComposeViewModel
import com.example.app.data.ErrorResponse
import com.example.app.data.repository.task.TaskRepository
import com.example.app.domain.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
  private val taskRepository: TaskRepository,
  private val homeUiMapper: HomeUiMapper,
) : ComposeViewModel<HomeState, HomeEvent>() {
  private var tasksState by mutableStateOf<Either<ErrorResponse, List<Task>>?>(null)

  @Composable
  override fun uiState(): HomeState {
    LaunchedEffect(Unit) {
      fetchTasks()
    }

    return homeUiMapper.map(tasksState)
  }

  private suspend fun fetchTasks() {
    tasksState = taskRepository.getTasks()
  }

  override fun onEvent(event: HomeEvent) {
    // TODO
  }
}