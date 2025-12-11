package com.example.app.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.example.app.common.ComposeViewModel
import com.example.app.data.ErrorResponse
import com.example.app.data.repository.task.TaskRepository
import com.example.app.domain.model.Task
import com.example.app.navigation.Navigator
import com.example.app.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
  private val taskRepository: TaskRepository,
  private val homeUiMapper: HomeUiMapper,
  private val navigator: Navigator,
) : ComposeViewModel<HomeState, HomeEvent>() {
  private var searchText by mutableStateOf("")
  private var tasksState by mutableStateOf<Either<ErrorResponse, List<Task>>?>(null)
  private var tasksRefreshing by mutableStateOf(false)

  @Composable
  override fun uiState(): HomeState {
    LaunchedEffect(Unit) {
      fetchTasks()
    }

    return homeUiMapper.map(
      searchText = searchText,
      tasksResponse = tasksState,
      isRefreshing = tasksRefreshing,
    )
  }

  private suspend fun fetchTasks() {
    tasksState = taskRepository.getTasks()
  }

  override fun onEvent(event: HomeEvent) {
    when (event) {
      is HomeEvent.SearchTextChange -> handleSearchTextChange(event)
      HomeEvent.ScanQrCodeClick -> scanQrCodeClick()
      HomeEvent.SettingsClick -> handleSettingsClick()
      HomeEvent.RefreshTasks -> handleRefreshTasks()
      HomeEvent.RetryClick -> handleRetryClick()
    }
  }

  private fun handleSearchTextChange(event: HomeEvent.SearchTextChange) {
    searchText = event.text
  }

  private fun scanQrCodeClick() {
    // TODO
  }

  private fun handleRefreshTasks() {
    viewModelScope.launch {
      tasksRefreshing = true
      tasksState = taskRepository.getTasks()
      tasksRefreshing = false
    }
  }

  private fun handleRetryClick() {
    viewModelScope.launch {
      tasksState = null
      tasksState = taskRepository.getTasks()
    }
  }

  private fun handleSettingsClick() {
    viewModelScope.launch {
      navigator.navigateTo(Screen.Settings)
    }
  }
}