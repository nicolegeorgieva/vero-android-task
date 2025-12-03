package com.example.app.common

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ResourceProvider @Inject constructor(
  @param:ApplicationContext
  private val context: Context,
) {
  fun getString(@StringRes id: Int): String {
    return context.getString(id)
  }
}