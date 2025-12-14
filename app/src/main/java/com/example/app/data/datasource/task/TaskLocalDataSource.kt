package com.example.app.data.datasource.task

import com.example.app.data.database.task.TaskDao
import com.example.app.data.database.task.TaskEntity
import javax.inject.Inject

class TaskLocalDataSource @Inject constructor(private val dao: TaskDao) {
  suspend fun getAllTasks(): List<TaskEntity> {
    return dao.getTasks()
  }

  suspend fun insertAllTasks(task: List<TaskEntity>) {
    dao.saveTasks(task)
  }

  suspend fun deleteTasks() {
    dao.deleteTasks()
  }
}