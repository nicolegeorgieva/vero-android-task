package com.example.app.navigation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.Snapshot
import androidx.compose.runtime.snapshots.SnapshotStateList
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator @Inject constructor() {
  val backStack: SnapshotStateList<Screen> = mutableStateListOf(Screen.Login)

  fun navigateTo(screen: Screen) {
    backStack.add(screen)
  }

  fun back() {
    if (backStack.size > 1) {
      backStack.removeLastOrNull()
    }
  }

  fun replace(screens: List<Screen>) {
    Snapshot.withMutableSnapshot {
      backStack.clear()
      backStack.addAll(screens)
    }
  }
}