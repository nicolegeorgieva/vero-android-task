package com.example.app.ui.login.component

import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.app.R

@Composable
fun LoginCtaButton(
  loading: Boolean,
  modifier: Modifier = Modifier,
  onClick: () -> Unit,
) {
  Button(
    modifier = modifier,
    onClick = onClick,
    enabled = !loading,
  ) {
    if (loading) {
      CircularProgressIndicator()
    } else {
      Text(stringResource(R.string.login_cta_button))
    }
  }
}