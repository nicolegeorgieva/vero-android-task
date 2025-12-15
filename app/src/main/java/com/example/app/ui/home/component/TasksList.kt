package com.example.app.ui.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.app.ui.home.TaskUi
import kotlinx.collections.immutable.ImmutableList

@Composable
fun TasksList(
  tasks: ImmutableList<TaskUi>,
  isRefreshing: Boolean,
  paddingValues: PaddingValues,
  modifier: Modifier = Modifier,
  onRefresh: () -> Unit,
) {
  PullToRefreshBox(
    isRefreshing = isRefreshing,
    onRefresh = onRefresh,
    modifier = modifier
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
        Spacer(Modifier.height(12.dp))
      }
    }
  }
}

@Composable
fun TaskCard(
  task: TaskUi,
  modifier: Modifier = Modifier,
) {
  val backgroundColor = task.color
  var cardExpanded by rememberSaveable { mutableStateOf(false) }

  ElevatedCard(
    modifier = modifier.fillMaxWidth(),
    colors = CardDefaults.elevatedCardColors(
      containerColor = backgroundColor ?: Color.Unspecified,
      contentColor = if (backgroundColor != null) {
        if (backgroundColor.luminance() <= 0.5) Color.White else Color.Black
      } else {
        contentColorFor(Color.Unspecified)
      }
    ),
    onClick = {
      cardExpanded = !cardExpanded
    }
  ) {
    Column(
      modifier = Modifier.padding(12.dp)
    ) {
      Text(
        text = task.id,
        style = MaterialTheme.typography.labelMedium,
      )
      Spacer(Modifier.height(8.dp))
      Text(
        text = task.title,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.bodyLarge,
      )
      if (task.description != null) {
        Spacer(Modifier.height(12.dp))
        Text(
          text = task.description,
          maxLines = if (cardExpanded) Int.MAX_VALUE else 6,
          overflow = TextOverflow.Ellipsis,
        )
      }
    }
  }
}