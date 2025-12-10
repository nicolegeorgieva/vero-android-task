package com.example.app.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.app.R

@Composable
fun ErrorUi(
  message: String,
  paddingValues: PaddingValues,
  retryLoading: Boolean,
  modifier: Modifier = Modifier,
  onRetry: () -> Unit,
) {
  Box(
    modifier = modifier
      .fillMaxSize()
      .padding(paddingValues)
      .padding(16.dp),
    contentAlignment = Alignment.Center,
  ) {
    Text(text = message)
    Spacer(Modifier.height(24.dp))
    CtaButton(
      text = stringResource(R.string.error_retry),
      loading = retryLoading,
      onClick = onRetry,
    )
  }
}