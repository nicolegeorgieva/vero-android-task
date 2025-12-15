package com.example.app.domain

import com.example.app.data.datasource.task.TaskLocalDataSource
import com.example.app.data.datastore.SessionStorage
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
  private val sessionStorage: SessionStorage,
  private val taskDataSource: TaskLocalDataSource,
) {
  suspend fun logout() {
    sessionStorage.remove()
    taskDataSource.deleteTasks()
  }
}