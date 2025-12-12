package com.example.app.ui.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.app.qrcodescanner.CameraPreview

@Composable
fun QrCodeScannerUi(onQrCodeScanned: (String) -> Unit) {
  Column(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    CameraPreview(onQrCodeScanned = onQrCodeScanned)
  }
}