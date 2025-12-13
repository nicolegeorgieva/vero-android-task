package com.example.app.data.database.task

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskEntity(
  @PrimaryKey val id: String,
  val title: String,
  val description: String?,
  val colorCode: String?,
)