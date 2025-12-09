package com.example.app.ui.home.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
  searchText: String,
  modifier: Modifier = Modifier,
  onTextChange: (String) -> Unit,
  onQrIconClick: () -> Unit,
  onSettingsClick: () -> Unit,
) {
  TopAppBar(
    modifier = modifier,
    title = {
      SearchField(
        text = searchText,
        onTextChange = onTextChange,
        onQrIconClick = onQrIconClick,
      )
    },
    actions = {
      SettingsButton(onClick = onSettingsClick)
    }
  )
}

@Composable
private fun SearchField(
  text: String,
  modifier: Modifier = Modifier,
  onTextChange: (String) -> Unit,
  onQrIconClick: () -> Unit,
) {
  OutlinedTextField(
    modifier = modifier,
    value = text,
    onValueChange = onTextChange,
    leadingIcon = {
      SearchIcon()
    },
    trailingIcon = {
      QrCodeIcon(onClick = onQrIconClick)
    }
  )
}

@Composable
private fun SearchIcon(modifier: Modifier = Modifier) {
  Icon(
    modifier = modifier,
    painter = painterResource(R.drawable.outline_search_24),
    contentDescription = null,
  )
}

@Composable
private fun QrCodeIcon(
  modifier: Modifier = Modifier,
  onClick: () -> Unit,
) {
  IconButton(
    modifier = modifier,
    onClick = onClick,
  ) {
    Icon(
      painter = painterResource(R.drawable.outline_qr_code_scanner_24),
      contentDescription = null,
    )
  }
}

@Composable
private fun SettingsButton(
  modifier: Modifier = Modifier,
  onClick: () -> Unit,
) {
  IconButton(
    modifier = modifier,
    onClick = onClick,
  ) {
    Icon(
      painter = painterResource(R.drawable.outline_settings_24),
      contentDescription = null,
    )
  }
}