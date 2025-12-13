package com.example.app.data.database.task

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface TaskDao {
  @Query("SELECT * FROM taskentity")
  suspend fun getTasks(): List<TaskEntity>

  @Upsert
  suspend fun saveTasks(task: List<TaskEntity>)

  @Delete
  suspend fun deleteTasks()
}