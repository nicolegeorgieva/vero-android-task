package com.example.app.ui.home.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.app.ui.home.TaskUi
import kotlinx.collections.immutable.ImmutableList

@Composable
fun TasksList(
  tasks: ImmutableList<TaskUi>,
  paddingValues: PaddingValues,
  modifier: Modifier = Modifier,
) {
  LazyColumn(modifier = modifier.padding(paddingValues)) {
    items(tasks) {
      // TODO - implement TaskCard
    }
  }
}