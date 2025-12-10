package com.example.app.ui.model

sealed interface Loadable<out T> {
  data class Content<T>(val value: T) : Loadable<T>
  data object Loading : Loadable<Nothing>
}