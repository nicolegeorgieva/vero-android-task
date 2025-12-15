package com.example.app.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.app.MainEventBus
import com.example.app.data.database.MyAppDatabase
import com.example.app.data.database.task.TaskDao
import com.example.app.data.datastore.SessionStorage
import com.example.app.domain.LogoutUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.AttributeKey
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import javax.inject.Singleton
import kotlin.time.ExperimentalTime

private val AuthenticatedAttributeKey = AttributeKey<Boolean>("authenticated-attribute")

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
  @Provides
  @Singleton
  fun provideHttpClient(
    json: Json,
    sessionStorage: SessionStorage,
    logoutUseCase: LogoutUseCase,
    mainEventBus: MainEventBus,
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
      install(Auth) {
        bearer {
          // If true, sends the auth bearer header without waiting for 401 response
          sendWithoutRequest { request ->
            request.isAuthenticated()
          }
          loadTokens {
            val session = sessionStorage.get()

            if (session != null) {
              BearerTokens(
                accessToken = session.accessToken,
                refreshToken = null,
              )
            } else {
              null
            }
          }
        }
      }
      install(KtorLogoutPlugin) {
        this.logoutUseCase = logoutUseCase
        this.mainEventBus = mainEventBus
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

  @Provides
  fun provideDatabase(
    @ApplicationContext
    context: Context,
  ): MyAppDatabase {
    return Room.databaseBuilder(
      context,
      MyAppDatabase::class.java, "my-app-database"
    )
      .fallbackToDestructiveMigration(dropAllTables = true)
      .build()
  }

  @Provides
  fun provideTaskDao(
    database: MyAppDatabase,
  ): TaskDao {
    return database.taskDao()
  }
}

typealias LocalDataStore = DataStore<Preferences>

fun HttpRequestBuilder.authenticated() {
  attributes[AuthenticatedAttributeKey] = true
}

fun HttpRequestBuilder.isAuthenticated(): Boolean {
  return attributes.getOrNull(AuthenticatedAttributeKey) == true
}