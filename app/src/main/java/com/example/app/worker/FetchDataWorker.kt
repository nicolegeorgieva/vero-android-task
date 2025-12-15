package com.example.app.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.app.data.repository.task.TaskRepository
import com.example.app.utils.Logger
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class FetchDataWorker @AssistedInject constructor(
  @Assisted appContext: Context,
  @Assisted workerParams: WorkerParameters,
  private val taskRepository: TaskRepository,
  private val logger: Logger,
) : CoroutineWorker(appContext, workerParams) {
  companion object {
    const val TAG = "FetchDataWorker"
  }

  override suspend fun doWork(): Result {
    logger.debug(TAG) { "Starting network fetch..." }
    return taskRepository.refresh(showLoading = false).fold(
      ifLeft = {
        if (runAttemptCount < 3) {
          Result.retry()
        } else {
          logger.error(TAG) { "Background fetch failed $runAttemptCount times." }
          Result.failure()
        }
      },
      ifRight = {
        logger.info(TAG) { "Background fetch successful." }
        Result.success()
      }
    )
  }
}