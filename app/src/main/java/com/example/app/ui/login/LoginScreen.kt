package com.example.app.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.app.R
import com.example.app.ui.login.component.LoginCtaButton
import com.example.app.ui.login.component.LoginInputField
import com.example.app.ui.login.component.LoginInputType

@Composable
fun LoginScreen() {
  val viewModel: LoginViewModel = hiltViewModel()

  LoginUi(
    uiState = viewModel.uiState(),
    onEvent = viewModel::onEvent,
  )
}

@Composable
fun LoginUi(
  uiState: LoginState,
  onEvent: (LoginEvent) -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Text(
      text = stringResource(R.string.login_title),
      style = MaterialTheme.typography.headlineMedium,
    )
    Spacer(Modifier.height(24.dp))
    LoginInputField(
      input = LoginInputType.Username(
        value = uiState.username,
        error = uiState.usernameError,
      ),
      onValueChange = {
        onEvent(LoginEvent.ChangeUsername(it))
      },
    )
    Spacer(Modifier.height(12.dp))
    LoginInputField(
      input = LoginInputType.Password(
        value = uiState.password,
        error = uiState.passwordError,
      ),
      onValueChange = {
        onEvent(LoginEvent.ChangePassword(it))
      },
      onDone = {
        onEvent(LoginEvent.LoginClick)
      }
    )
    Spacer(Modifier.height(24.dp))
    LoginCtaButton(
      loading = uiState.loginButtonLoading,
      onClick = {
        onEvent(LoginEvent.LoginClick)
      }
    )
  }
}