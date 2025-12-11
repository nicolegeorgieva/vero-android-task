package com.example.app.domain

import com.example.app.domain.model.Task
import javax.inject.Inject

class SearchUseCase @Inject constructor() {
  fun search(
    tasks: List<Task>,
    query: String,
  ): List<Task> {
    return tasks.filter { task ->
      task.id.contains(other = query, ignoreCase = true) ||
          task.title.contains(other = query, ignoreCase = true) ||
          task.description.contains(other = query, ignoreCase = true) ||
          task.colorHex.contains(other = query, ignoreCase = true)
    }
  }
}