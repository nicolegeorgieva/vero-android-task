package com.example.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.app.ui.home.HomeScreen
import com.example.app.ui.login.LoginScreen
import com.example.app.ui.settings.SettingsScreen

@Composable
fun Navigation(
  navController: NavHostController,
  startDestination: Screen,
) {
  NavHost(
    navController = navController,
    startDestination = startDestination,
  ) {
    composable<Screen.Login> {
      LoginScreen()
    }
    composable<Screen.Home> {
      HomeScreen()
    }
    composable<Screen.Settings> {
      SettingsScreen()
    }
  }
}