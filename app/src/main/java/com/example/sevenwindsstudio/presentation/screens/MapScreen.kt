package com.example.sevenwindsstudio.presentation.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.sevenwindsstudio.R
import com.example.sevenwindsstudio.presentation.navigation.Screen
import com.example.sevenwindsstudio.presentation.viewmodels.LocationsViewModel
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider

@Composable
fun MapScreen(
    navController: NavController,
    selectedLocationId: Int? = null,
    viewModel: LocationsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val mapView = remember { MapView(context) }
    val state by viewModel.uiState.collectAsState()
    var userLocation by remember { mutableStateOf<Point?>(null) }
    val placemarkTapListeners = remember { mutableSetOf<MapObjectTapListener>() }

    // Инициализация карты
    DisposableEffect(Unit) {
        MapKitFactory.initialize(context)
        mapView.onStart()
        MapKitFactory.getInstance().onStart()

        onDispose {
            placemarkTapListeners.clear()
            mapView.onStop()
            MapKitFactory.getInstance().onStop()
        }
    }

    // Получение текущего местоположения
    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                ?: locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            lastKnownLocation?.let {
                userLocation = Point(it.latitude, it.longitude)
            }
        }
    }

    // Обработка маркеров кофеен
    LaunchedEffect(state.locations, selectedLocationId, userLocation) {
        val map = mapView.map
        val collection = map.mapObjects.addCollection()

        collection.clear()
        placemarkTapListeners.clear()

        state.locations.forEach { locationItem ->
            val point = Point(
                locationItem.location.latitude,
                locationItem.location.longitude
            )

            // Создание маркера в виде стаканчика
            val placemark = collection.addPlacemark(point).apply {
                setIcon(ImageProvider.fromResource(context, R.drawable.ic_coffee_cup))
                userData = locationItem.location.id
            }

            // Обработчик нажатия на маркер
            val tapListener = MapObjectTapListener { mapObject, _ ->
                (mapObject.userData as? Int)?.let { locationId ->
                    navController.navigate(Screen.Menu.createRoute(locationId))
                }
                true
            }

            placemark.addTapListener(tapListener)
            placemarkTapListeners.add(tapListener)
        }

        // Позиционирование камеры
        val targetPoint = selectedLocationId?.let { id ->
            state.locations.find { it.location.id == id }?.let { location ->
                Point(location.location.latitude, location.location.longitude)
            }
        } ?: userLocation ?: Point(55.751574, 37.573856) // Москва по умолчанию

        map.move(
            CameraPosition(
                targetPoint,
                when {
                    selectedLocationId != null -> 15f
                    userLocation != null -> 13f
                    else -> 11f
                },
                0f,
                0f
            )
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { mapView },
            modifier = Modifier.fillMaxSize()
        )
    }
}

// Вспомогательные функции
fun cleanLocationName(name: String): String {
    return name.replace("[0-9]".toRegex(), "")
        .replace("\\s+".toRegex(), " ")
        .trim()
}

private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Float {
    val results = FloatArray(1)
    Location.distanceBetween(lat1, lon1, lat2, lon2, results)
    return results[0]
}

private fun formatDistance(distance: Float): String {
    return if (distance < 1000) {
        "${distance.toInt()} м"
    } else {
        "%.1f км".format(distance / 1000)
    }
}