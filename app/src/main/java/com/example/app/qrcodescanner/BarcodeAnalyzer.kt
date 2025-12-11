package com.example.app.qrcodescanner

import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage

@OptIn(ExperimentalGetImage::class)
class BarcodeAnalyzer(private val onQrCodeScanned: (String) -> Unit) : ImageAnalysis.Analyzer {
  private val scanner = BarcodeScanning.getClient()

  override fun analyze(imageProxy: ImageProxy) {
    val mediaImage = imageProxy.image ?: return
    val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

    scanner.process(image)
      .addOnSuccessListener { barcodes ->
        barcodes.forEach { barcode ->
          barcode.rawValue?.let { onQrCodeScanned(it) }
        }
      }
      .addOnCompleteListener { imageProxy.close() }
  }
}