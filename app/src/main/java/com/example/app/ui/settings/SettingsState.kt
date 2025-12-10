package com.example.app.ui.settings

import kotlinx.serialization.Serializable

@Serializable
data class SettingsState(
  val logoutButtonLoading: Boolean,
)