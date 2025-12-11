package com.example.app.qrcodescanner

import androidx.compose.runtime.Immutable

@Immutable
data class QrCodeScannerState(
  val scannedText: String?,
)
