package com.example.app.ui.login.component

import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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
  onDone: (KeyboardActionScope.() -> Unit)? = null,
) {
  OutlinedTextField(
    modifier = modifier,
    value = input.value,
    label = {
      LoginInputFieldTitle(input = input)
    },
    placeholder = {
      LoginInputFieldTitle(input = input)
    },
    keyboardOptions = KeyboardOptions(
      imeAction = if (input is LoginInputType.Username) ImeAction.Next else ImeAction.Done
    ),
    keyboardActions = if (onDone != null && input is LoginInputType.Password) {
      KeyboardActions(onDone = onDone)
    } else {
      KeyboardActions.Default
    },
    onValueChange = onValueChange,
    isError = input.error != null,
    visualTransformation = if (input is LoginInputType.Password) {
      PasswordVisualTransformation()
    } else {
      VisualTransformation.None
    },
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