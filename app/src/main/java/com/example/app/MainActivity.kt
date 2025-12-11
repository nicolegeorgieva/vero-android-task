package com.example.app

import android.Manifest
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
import androidx.navigation.compose.rememberNavController
import com.example.app.domain.SessionUseCase
import com.example.app.navigation.Navigation
import com.example.app.navigation.NavigationEvent
import com.example.app.navigation.Navigator
import com.example.app.navigation.Screen
import com.example.app.theme.MyAppTheme
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  @Inject
  lateinit var navigator: Navigator

  @Inject
  lateinit var sessionUseCase: SessionUseCase

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
          val navController = rememberNavController()

          LaunchedEffect(Unit) {
            navigator.navigationEvents.collectLatest { event ->
              when (event) {
                is NavigationEvent.GoTo -> navController.navigate(event.screen)
                NavigationEvent.Back -> navController.popBackStack()
              }
            }
          }

          var startDestination by remember { mutableStateOf<Screen?>(null) }

          LaunchedEffect(Unit) {
            startDestination = if (sessionUseCase.getSession() != null) {
              Screen.Home
            } else {
              Screen.Login
            }
          }

          startDestination?.let {
            Navigation(
              navController = navController,
              startDestination = it,
            )
          }

          val cameraPermissionState =
            rememberPermissionState(permission = Manifest.permission.CAMERA)

          LaunchedEffect(Unit) {
            if (!cameraPermissionState.status.isGranted) {
              cameraPermissionState.launchPermissionRequest()
            }
          }
        }
      }
    }
  }
}