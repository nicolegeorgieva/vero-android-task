package com.example.app.ui.home

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen() {
  val viewModel: HomeViewModel = hiltViewModel()

  HomeUi(onEvent = viewModel::onEvent)
}

@Composable
fun HomeUi(onEvent: (HomeEvent) -> Unit) {
  // TODO
}