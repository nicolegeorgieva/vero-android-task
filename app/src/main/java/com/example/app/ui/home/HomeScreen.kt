package com.example.app.ui.home

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.app.ui.component.ErrorUi
import com.example.app.ui.component.Loading
import com.example.app.ui.home.component.HomeTopBar
import com.example.app.ui.home.component.QrCodeScannerUi
import com.example.app.ui.home.component.TasksList
import com.example.app.ui.model.Loadable
import kotlinx.collections.immutable.ImmutableList

@Composable
fun HomeScreen() {
  val viewModel: HomeViewModel = hiltViewModel()

  HomeUi(
    uiState = viewModel.uiState(),
    onEvent = viewModel::onEvent,
  )
}

@Composable
fun HomeUi(
  uiState: HomeState,
  onEvent: (HomeEvent) -> Unit,
) {
  Scaffold(
    topBar = {
      if (uiState is HomeState.Searchable) {
        HomeTopBar(
          searchText = uiState.searchQuery,
          onTextChange = {
            onEvent(HomeEvent.SearchTextChange(it))
          },
          onQrIconClick = {
            onEvent(HomeEvent.ScanQrCodeClick)
          },
          onSettingsClick = {
            onEvent(HomeEvent.SettingsClick)
          },
        )
      }
    },
    content = { paddingValues ->
      when (uiState) {
        is HomeState.Content -> when (uiState.tasks) {
          is Loadable.Content<ImmutableList<TaskUi>> -> TasksList(
            tasks = uiState.tasks.value,
            isRefreshing = uiState.isRefreshing,
            paddingValues = paddingValues,
            onRefresh = {
              onEvent(HomeEvent.RefreshTasks)
            }
          )

          Loadable.Loading -> Loading(paddingValues = paddingValues)
        }

        is HomeState.ScanQrCode -> QrCodeScannerUi(
          onQrCodeScanned = {
            onEvent(HomeEvent.ScanQrCode(it))
          }
        )

        is HomeState.Error -> ErrorUi(
          message = uiState.message,
          paddingValues = paddingValues,
          onRetry = {
            onEvent(HomeEvent.RetryClick)
          }
        )
      }
    }
  )
}