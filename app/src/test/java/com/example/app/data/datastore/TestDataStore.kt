package com.example.app.data.datastore

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.example.app.di.LocalDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.updateAndGet

class TestDataStore : LocalDataStore {
  private val _data = MutableStateFlow(emptyPreferences())
  override val data: Flow<Preferences> = _data

  override suspend fun updateData(
    transform: suspend (Preferences) -> Preferences
  ): Preferences {
    return _data.updateAndGet { transform(it) }
  }

  fun clear() {
    _data.value = emptyPreferences()
  }

  suspend operator fun <T> get(key: Preferences.Key<T>): T? {
    return data.map { it[key] }.first()
  }

  suspend operator fun <T> set(key: Preferences.Key<T>, value: T) {
    edit { it[key] = value }
  }
}