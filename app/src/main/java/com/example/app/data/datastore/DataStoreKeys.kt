package com.example.app.data.datastore

import androidx.datastore.preferences.core.stringPreferencesKey

object DataStoreKeys {
  val accessToken = stringPreferencesKey("access-token")
}