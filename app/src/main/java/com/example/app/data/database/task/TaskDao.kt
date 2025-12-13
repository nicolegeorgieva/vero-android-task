package com.example.app.data.database.task

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TaskDao {
  @Query("SELECT * FROM taskentity")
  fun getAllTasks(): List<TaskEntity>

  @Insert
  fun insertAllTasks(task: List<TaskEntity>)

  @Delete
  fun deleteAllTasks()
}