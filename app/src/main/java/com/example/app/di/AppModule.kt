package com.example.app.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import javax.inject.Singleton
import kotlin.time.ExperimentalTime

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
  @Provides
  @Singleton
  fun provideHttpClient(
    json: Json
  ): HttpClient {
    return HttpClient {
      install(ContentNegotiation) {
        json(
          json = json,
          contentType = ContentType.Any
        )
      }
      install(DefaultRequest) {
        contentType(ContentType.Application.Json)
        url(urlString = "https://api.baubuddy.de/")
      }
    }
  }

  @OptIn(ExperimentalTime::class)
  @Provides
  fun provideJson(): Json {
    return Json {
      ignoreUnknownKeys = true
      isLenient = true
      serializersModule = SerializersModule {
        contextual(InstantSerializer)
      }
    }
  }

  private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app-datastore")

  @Provides
  fun provideDataStore(
    @ApplicationContext
    context: Context,
  ): LocalDataStore {
    return context.dataStore
  }
}

typealias LocalDataStore = DataStore<Preferences>