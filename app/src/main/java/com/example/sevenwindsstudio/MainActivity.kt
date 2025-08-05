package com.example.sevenwindsstudio

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.navigation.compose.rememberNavController
import com.example.sevenwindsstudio.presentation.navigation.NavGraph
import com.example.sevenwindsstudio.utils.LocationPermissionManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var locationPermissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        locationPermissionLauncher = LocationPermissionManager.createPermissionLauncher(
            activity = this,
            onGranted = { initApp() },
            onDenied = { showPermissionDenied() }
        )

        if (LocationPermissionManager.hasLocationPermissions(this)) {
            initApp()
        } else {
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        locationPermissionLauncher.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ))
    }

    private fun showPermissionDenied() {
        Toast.makeText(this, "Location permission required", Toast.LENGTH_LONG).show()
    }

    private fun initApp() {
        setContent {
            CoffeeAppTheme {
                val navController = rememberNavController()
                NavGraph(navController)
            }
        }
    }
}