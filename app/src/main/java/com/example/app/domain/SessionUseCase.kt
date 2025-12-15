package com.example.app.domain

import arrow.core.Either
import com.example.app.data.datastore.SessionStorage
import com.example.app.data.repository.login.LoginError
import com.example.app.data.repository.login.LoginRepository
import com.example.app.domain.model.Session
import com.example.app.worker.BackgroundWorkerManager
import javax.inject.Inject

class SessionUseCase @Inject constructor(
  private val loginRepository: LoginRepository,
  private val sessionStorage: SessionStorage,
  private val workerManager: BackgroundWorkerManager,
) {
  suspend fun getSession(): Session? {
    return sessionStorage.get()
  }

  suspend fun login(username: String, password: String): Either<LoginError, Session> {
    return loginRepository.login(
      username = username,
      password = password,
    ).onRight { session ->
      sessionStorage.store(session)
      workerManager.start()
    }
  }
}