package com.example.app.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppTopAppBar(
  title: String,
  modifier: Modifier = Modifier,
  navigateBack: Boolean = false,
  onBackClick: (() -> Unit)? = null,
) {
  TopAppBar(
    modifier = modifier,
    title = {
      Text(text = title)
    },
    navigationIcon = if (navigateBack && onBackClick != null) {
      { BackButton(onBackClick = onBackClick) }
    } else {
      {}
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
      imageVector = Icons.Filled.ArrowBack,
      contentDescription = null,
    )
  }
}