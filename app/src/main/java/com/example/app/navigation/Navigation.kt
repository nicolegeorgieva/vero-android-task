package com.example.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.app.ui.home.HomeScreen
import com.example.app.ui.login.LoginScreen
import com.example.app.ui.settings.SettingsScreen

@Composable
fun Navigation(navigator: Navigator) {
  NavDisplay(
    backStack = navigator.backStack,
    onBack = navigator::back,
    entryProvider = entryProvider {
      entry<Screen.Login> {
        LoginScreen()
      }
      entry<Screen.Home> {
        HomeScreen()
      }
      entry<Screen.Settings> {
        SettingsScreen()
      }
    }
  )
}