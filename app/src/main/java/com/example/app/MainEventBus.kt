package com.example.app

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainEventBus @Inject constructor() {
  private val _events = MutableSharedFlow<MainEvent>()
  val events: Flow<MainEvent> = _events

  suspend fun emit(event: MainEvent) {
    _events.emit(event)
  }
}

sealed interface MainEvent {
  data object InvalidSession : MainEvent
}