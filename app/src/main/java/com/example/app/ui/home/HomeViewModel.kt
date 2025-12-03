package com.example.app.ui.home

import androidx.compose.runtime.Composable
import com.example.app.common.ComposeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ComposeViewModel<HomeState, HomeEvent>() {
  @Composable
  override fun uiState(): HomeState {
    return HomeState()
  }

  override fun onEvent(event: HomeEvent) {
    // TODO
  }
}