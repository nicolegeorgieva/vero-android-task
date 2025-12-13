package com.example.app.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.app.data.database.task.TaskDao
import com.example.app.data.database.task.TaskEntity

@Database(entities = [TaskEntity::class], version = 1)
abstract class MyAppDatabase : RoomDatabase() {
  abstract fun taskDao(): TaskDao
}