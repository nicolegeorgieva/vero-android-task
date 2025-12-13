package com.example.app.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.app.common.ComposeViewModel
import com.example.app.data.repository.task.TaskRepository
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
  private var qrScannerVisible by mutableStateOf(false)
  private var searchQuery by mutableStateOf("")
  private var tasksRefreshing by mutableStateOf(false)

  @Composable
  override fun uiState(): HomeState {
    val tasksRes by remember { taskRepository.getTasks(viewModelScope) }
      .collectAsState(initial = null)

    if (qrScannerVisible) {
      return HomeState.ScanQrCode
    }

    return homeUiMapper.map(
      searchQuery = searchQuery,
      tasksResponse = tasksRes,
      isRefreshing = tasksRefreshing,
    )
  }

  override fun onEvent(event: HomeEvent) {
    when (event) {
      is HomeEvent.SearchTextChange -> handleSearchTextChange(event)
      HomeEvent.ScanQrCodeClick -> scanQrCodeClick()
      is HomeEvent.ScanQrCode -> handleScanQrCode(event)
      HomeEvent.SettingsClick -> handleSettingsClick()
      HomeEvent.RefreshTasks -> handleRefreshTasks()
      HomeEvent.RetryClick -> handleRetryClick()
    }
  }

  private fun handleSearchTextChange(event: HomeEvent.SearchTextChange) {
    searchQuery = event.text
  }

  private fun scanQrCodeClick() {
    qrScannerVisible = true
  }

  private fun handleScanQrCode(event: HomeEvent.ScanQrCode) {
    qrScannerVisible = false
    searchQuery = event.scannedText
  }

  private fun handleRefreshTasks() {
    viewModelScope.launch {
      tasksRefreshing = true
      taskRepository.refresh(showLoading = false)
      tasksRefreshing = false
    }
  }

  private fun handleRetryClick() {
    viewModelScope.launch {
      taskRepository.refresh(showLoading = true)
    }
  }

  private fun handleSettingsClick() {
    viewModelScope.launch {
      navigator.navigateTo(Screen.Settings)
    }
  }
}