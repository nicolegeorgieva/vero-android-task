package com.example.app.utils

import android.util.Log
import com.example.app.BuildConfig
import javax.inject.Inject

class Logger @Inject constructor() {
  fun debug(tag: String? = null, message: () -> String) {
    if (!BuildConfig.DEBUG) return

    Log.d(tag, message())
  }

  fun info(tag: String? = null, message: () -> String) {
    if (!BuildConfig.DEBUG) return

    Log.i(tag, message())
  }

  fun error(tag: String? = null, message: () -> String) {
    if (!BuildConfig.DEBUG) return

    Log.e(tag, message())
  }
}