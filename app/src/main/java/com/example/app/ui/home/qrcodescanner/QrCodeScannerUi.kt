package com.example.app.ui.home.qrcodescanner

import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.app.R
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@Composable
fun QrCodeScannerUi(onQrCodeScanned: (String) -> Unit) {
  val cameraPermissionState =
    rememberPermissionState(permission = Manifest.permission.CAMERA)

  LaunchedEffect(Unit) {
    if (!cameraPermissionState.status.isGranted) {
      cameraPermissionState.launchPermissionRequest()
    }
  }

  if (cameraPermissionState.status.isGranted) {
    CameraPreview(onQrCodeScanned = onQrCodeScanned)
  } else {
    CameraPermissionUi(
      onClick = {
        cameraPermissionState.launchPermissionRequest()
      }
    )
  }
}

@Composable
private fun CameraPermissionUi(
  modifier: Modifier = Modifier,
  onClick: () -> Unit,
) {
  Column(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
  ) {
    Text(
      text = stringResource(R.string.home_allow_camera_message),
      textAlign = TextAlign.Center,
    )
    Spacer(Modifier.height(24.dp))
    Button(onClick = onClick) {
      Text(stringResource(R.string.home_allow_camera_button))
    }
  }
}