package com.example.app.ui.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CtaButton(
  text: String,
  modifier: Modifier = Modifier,
  loading: Boolean = false,
  onClick: () -> Unit,
) {
  Button(
    modifier = modifier,
    onClick = onClick,
    enabled = !loading,
  ) {
    if (loading) {
      CircularProgressIndicator(modifier = Modifier.size(24.dp))
    } else {
      Text(text = text)
    }
  }
}