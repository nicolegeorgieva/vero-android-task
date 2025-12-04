package com.example.app.data.datasource.task

import kotlinx.serialization.Serializable

@Serializable
data class TaskDto(
  val task: String,
  val title: String,
  val description: String,
  val colorCode: String,
)