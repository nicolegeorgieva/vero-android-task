package com.example.app.ui.login.component

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.app.R

@Immutable
sealed interface LoginInputType {
  val value: String
  val error: String?

  data class Username(
    override val value: String,
    override val error: String?,
  ) : LoginInputType

  data class Password(
    override val value: String,
    override val error: String?,
  ) : LoginInputType
}

@Composable
fun LoginInputField(
  input: LoginInputType,
  modifier: Modifier = Modifier,
  onValueChange: (String) -> Unit,
) {
  TextField(
    modifier = modifier,
    value = input.value,
    label = {
      LoginInputFieldTitle(input = input)
    },
    placeholder = {
      LoginInputFieldTitle(input = input)
    },
    onValueChange = onValueChange,
    isError = input.error != null,
    supportingText = input.error?.let {
      {
        Text(text = it)
      }
    }
  )
}

@Composable
private fun LoginInputFieldTitle(
  input: LoginInputType,
  modifier: Modifier = Modifier,
) {
  Text(
    modifier = modifier,
    text = stringResource(
      when (input) {
        is LoginInputType.Username -> R.string.login_username_field_title
        is LoginInputType.Password -> R.string.login_password_field_title
      }
    )
  )
}