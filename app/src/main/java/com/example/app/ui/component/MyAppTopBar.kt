package com.example.app.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MyAppTopBar(
  title: String,
  modifier: Modifier = Modifier,
  onBackClick: (() -> Unit)? = null,
) {
  TopAppBar(
    modifier = modifier,
    title = {
      Text(text = title)
    },
    navigationIcon = {
      if (onBackClick != null) {
        BackButton(onBackClick = onBackClick)
      }
    }
  )
}

@Composable
private fun BackButton(
  modifier: Modifier = Modifier,
  onBackClick: () -> Unit,
) {
  IconButton(
    modifier = modifier,
    onClick = onBackClick,
  ) {
    Icon(
      imageVector = Icons.AutoMirrored.Filled.ArrowBack,
      contentDescription = null,
    )
  }
}