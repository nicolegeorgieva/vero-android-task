package com.example.app.qrcodescanner

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.app.common.ComposeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QrScannerViewModel @Inject constructor() :
  ComposeViewModel<QrCodeScannerState, QrCodeScannerEvent>() {
  private var qrCodeText by mutableStateOf<String?>(null)

  @Composable
  override fun uiState(): QrCodeScannerState {
    return QrCodeScannerState(scannedText = qrCodeText)
  }

  override fun onEvent(event: QrCodeScannerEvent) {
    when (event) {
      is QrCodeScannerEvent.ScanQrCode -> handleScanQrCode(event)
    }
  }

  private fun handleScanQrCode(event: QrCodeScannerEvent.ScanQrCode) {
    qrCodeText = event.scannedText
  }
}