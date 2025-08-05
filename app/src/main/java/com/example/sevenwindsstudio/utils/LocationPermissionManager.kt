package com.example.sevenwindsstudio.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

class LocationPermissionManager {
    companion object {
        fun hasLocationPermissions(context: Context): Boolean {
            return ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        }

        fun createPermissionLauncher(
            activity: ComponentActivity,
            onGranted: () -> Unit,
            onDenied: () -> Unit
        ): ActivityResultLauncher<Array<String>> {
            return activity.registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { permissions ->
                when {
                    permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> onGranted()
                    permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> onGranted()
                    else -> onDenied()
                }
            }
        }
    }
}