package com.example.app.data.datasource.task

import arrow.core.Either
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class TaskDataSource @Inject constructor(
  private val httpClient: HttpClient,
) {
  suspend fun fetchTasks(): Either<Throwable, List<TaskDto>> {
    return Either.catch {
      httpClient.get("index.php/v1/tasks/select") {
        TODO()
      }.body<List<TaskDto>>()
    }
  }
}