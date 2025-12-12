package com.example.app.domain.model

data class Task(
  val id: String,
  val title: String,
  val description: String?,
  val colorHex: String?,
)