package com.example.sevenwindsstudio.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.sevenwindsstudio.presentation.navigation.Screen
import com.example.sevenwindsstudio.presentation.uistate.SplashUiState
import com.example.sevenwindsstudio.presentation.viewmodels.SplashViewModel
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        launch {
            viewModel.state.filter { it == SplashUiState.AUTHORIZED }
                .take(1)
                .collect {
                    navController.navigate(Screen.Locations.route) {
                        popUpTo(Screen.Splash.route) {
                            inclusive = true
                        }
                    }
                }
        }

        launch {
            viewModel.state.filter { it == SplashUiState.UNAUTHORIZED }
                .take(1)
                .collect {
                    navController.navigate(Screen.Register.route) {
                        popUpTo(Screen.Splash.route) {
                            inclusive = true
                        }
                    }
                }
        }
    }
}