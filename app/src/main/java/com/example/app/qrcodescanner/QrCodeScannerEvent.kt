package com.example.app.qrcodescanner

sealed interface QrCodeScannerEvent {
  data class ScanQrCode(val scannedText: String) : QrCodeScannerEvent
}