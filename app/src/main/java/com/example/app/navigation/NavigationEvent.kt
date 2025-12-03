package com.example.app.navigation

sealed interface NavigationEvent {
  data class GoTo(val screen: Screen) : NavigationEvent
  data object Back : NavigationEvent
}