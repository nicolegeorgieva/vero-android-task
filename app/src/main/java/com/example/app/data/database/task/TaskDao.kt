package com.example.app.data.database.task

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface TaskDao {
  @Query("SELECT * FROM taskentity")
  suspend fun getTasks(): List<TaskEntity>

  @Upsert
  suspend fun saveTasks(task: List<TaskEntity>)

  @Query("DELETE FROM taskentity")
  suspend fun deleteTasks()
}