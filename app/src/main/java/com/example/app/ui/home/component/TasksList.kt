package com.example.app.ui.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.app.ui.home.TaskUi
import kotlinx.collections.immutable.ImmutableList

@Composable
fun TasksList(
  tasks: ImmutableList<TaskUi>,
  paddingValues: PaddingValues,
  modifier: Modifier = Modifier,
) {
  LazyColumn(
    modifier = modifier
      .padding(paddingValues)
      .padding(16.dp)
  ) {
    items(
      items = tasks,
      key = {
        it.id
      },
    ) { task ->
      TaskCard(task = task)
    }
  }
}

@Composable
fun TaskCard(
  task: TaskUi,
  modifier: Modifier = Modifier,
) {
  Card(
    modifier = modifier,
    colors = CardDefaults.cardColors(containerColor = task.color ?: Color.Unspecified)
  ) {
    Row(modifier = Modifier.padding(12.dp)) {
      Text(
        text = task.id,
        style = MaterialTheme.typography.bodyLarge,
      )
      Spacer(Modifier.width(8.dp))
      Column {
        Text(
          text = task.title,
          style = MaterialTheme.typography.bodyLarge,
        )
        Spacer(Modifier.height(12.dp))
        Text(text = task.description)
      }
    }
  }
}