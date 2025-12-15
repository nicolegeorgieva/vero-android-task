package com.example.app.worker

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BackgroundWorkerManager @Inject constructor(
  @param:ApplicationContext
  private val context: Context
) {
  companion object {
    const val FETCH_WORKER = "fetch_data_worker"
  }

  private val workManager = WorkManager.getInstance(context)

  fun start() {
    val constraints = Constraints.Builder()
      .setRequiredNetworkType(NetworkType.CONNECTED)
      .build()

    val workRequest = PeriodicWorkRequestBuilder<FetchDataWorker>(
      60, TimeUnit.MINUTES
    ).setInitialDelay(60, TimeUnit.MINUTES)
      .setConstraints(constraints)
      .build()

    workManager.enqueueUniquePeriodicWork(
      FETCH_WORKER,
      ExistingPeriodicWorkPolicy.UPDATE,
      workRequest
    )
  }

  fun cancel() {
    workManager.cancelUniqueWork(FETCH_WORKER)
  }
}