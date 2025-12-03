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
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.app.navigation.Navigation
import com.example.app.navigation.NavigationEvent
import com.example.app.navigation.Navigator
import com.example.app.theme.MyAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  @Inject
  lateinit var navigator: Navigator

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

          Navigation(navController)
        }
      }
    }
  }
}