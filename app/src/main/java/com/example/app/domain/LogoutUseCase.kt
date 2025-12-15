package com.example.app.domain

import com.example.app.data.datasource.task.TaskLocalDataSource
import com.example.app.data.datastore.SessionStorage
import com.example.app.worker.BackgroundWorkerManager
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
  private val sessionStorage: SessionStorage,
  private val taskDataSource: TaskLocalDataSource,
  private val workerManager: BackgroundWorkerManager,
) {
  suspend fun logout() {
    sessionStorage.remove()
    taskDataSource.deleteTasks()
    workerManager.cancel()
  }
}