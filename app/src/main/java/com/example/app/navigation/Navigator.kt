package com.example.app.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator @Inject constructor() {
  val navigationEvents = MutableSharedFlow<NavigationEvent>()

  suspend fun navigateTo(screen: Screen) {
    navigationEvents.emit(NavigationEvent.GoTo(screen))
  }

  suspend fun back() {
    navigationEvents.emit(NavigationEvent.Back)
  }
}