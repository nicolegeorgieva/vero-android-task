package com.example.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.app.domain.SessionUseCase
import com.example.app.navigation.Navigation
import com.example.app.navigation.Navigator
import com.example.app.navigation.Screen
import com.example.app.theme.MyAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  @Inject
  lateinit var navigator: Navigator

  @Inject
  lateinit var sessionUseCase: SessionUseCase

  @Inject
  lateinit var mainEventBus: MainEventBus

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyAppTheme {
        Surface(
          modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.safeDrawing.asPaddingValues()),
          color = MaterialTheme.colorScheme.background,
        ) {
          var navigationReady by remember { mutableStateOf(false) }

          LaunchedEffect(Unit) {
            val startDestination = if (sessionUseCase.getSession() != null) {
              Screen.Home
            } else {
              Screen.Login
            }
            navigator.replace(listOf(startDestination))
            navigationReady = true
          }

          LaunchedEffect(Unit) {
            mainEventBus.events.collect { event ->
              when (event) {
                MainEvent.InvalidSession -> {
                  navigator.replace(listOf(Screen.Login))
                }
              }
            }
          }

          if (navigationReady) {
            Navigation(navigator = navigator)
          }
        }
      }
    }
  }
}