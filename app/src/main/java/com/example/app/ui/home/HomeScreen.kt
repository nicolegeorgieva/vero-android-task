package com.example.app.ui.home

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.app.ui.home.component.HomeTopBar

@Composable
fun HomeScreen() {
  val viewModel: HomeViewModel = hiltViewModel()

  HomeUi(onEvent = viewModel::onEvent)
}

@Composable
fun HomeUi(onEvent: (HomeEvent) -> Unit) {
  Scaffold(
    topBar = {
      HomeTopBar(
        searchText = "TODO",
        onTextChange = {},
        onQrIconClick = {},
        onSettingsClick = {
          onEvent(HomeEvent.SettingsClick)
        }
      )
    },
    content = { paddingValues ->
      LazyColumn(
        modifier = Modifier.padding(paddingValues)
      ) {
        // TODO
      }
    }
  )
}