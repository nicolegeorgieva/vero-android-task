package com.example.app.qrcodescanner

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun QrCodeScannerScreen() {
  val viewModel: QrScannerViewModel = hiltViewModel()

  QrCodeScannerUi(
    uiState = viewModel.uiState(),
    onEvent = viewModel::onEvent
  )
}

@Composable
private fun QrCodeScannerUi(
  uiState: QrCodeScannerState,
  onEvent: (QrCodeScannerEvent) -> Unit,
) {
  Column(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    CameraPreview(
      onQrCodeScanned = {
        onEvent(QrCodeScannerEvent.ScanQrCode(it))
      }
    )
    Spacer(modifier = Modifier.height(16.dp))
    Text(
      text = "Scanned Data: ${uiState.scannedText}",
      fontSize = 18.sp,
      fontWeight = FontWeight.Bold,
    )
  }
}