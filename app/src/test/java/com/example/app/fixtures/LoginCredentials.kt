package com.example.app.fixtures

val CORRECT_CREDENTIALS = Credentials(
  username = "test",
  password = "123"
)

data class Credentials(
  val username: String,
  val password: String,
)