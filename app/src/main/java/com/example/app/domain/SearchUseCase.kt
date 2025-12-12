package com.example.app.domain

import com.example.app.domain.model.Task
import javax.inject.Inject

class SearchUseCase @Inject constructor() {
  fun search(
    tasks: List<Task>,
    query: String,
  ): List<Task> {
    return tasks.filter { task ->
      task.id.matchesQuery(query) ||
          task.title.matchesQuery(query) ||
          task.description.matchesQuery(query) ||
          task.colorHex.matchesQuery(query)
    }
  }

  private fun String?.matchesQuery(query: String): Boolean {
    return this?.contains(other = query, ignoreCase = true) ?: false
  }
}