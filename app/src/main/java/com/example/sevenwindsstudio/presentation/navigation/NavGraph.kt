package com.example.sevenwindsstudio.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.sevenwindsstudio.presentation.screens.*
import com.example.sevenwindsstudio.presentation.uistate.MenuItemWithCount
import com.example.sevenwindsstudio.presentation.viewmodels.OrderViewModel

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) { SplashScreen(navController) }
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable(Screen.Register.route) { RegisterScreen(navController) }
        composable(Screen.Locations.route) { LocationsScreen(navController) }

        composable(Screen.Map.route) {
            MapScreen(navController, selectedLocationId = null)
        }

        composable(
            Screen.MapWithSelectedLocation.route,
            arguments = listOf(navArgument("locationId") { type = NavType.IntType })
        ) { backStackEntry ->
            val locationId = backStackEntry.arguments?.getInt("locationId") ?: 0
            MapScreen(navController, selectedLocationId = locationId)
        }

        composable(
            Screen.Menu.route,
            arguments = listOf(navArgument("locationId") { type = NavType.IntType })
        ) { backStackEntry ->
            val locationId = backStackEntry.arguments?.getInt("locationId") ?: 0
            MenuScreen(navController, locationId)
        }

        composable(Screen.Order.route) {
            val orderItems = remember {
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.get<List<MenuItemWithCount>>("orderItems") ?: emptyList()
            }

            val viewModel = hiltViewModel<OrderViewModel>()
            LaunchedEffect(orderItems) {
                viewModel.setOrderItems(orderItems)
            }

            OrderScreen(navController, viewModel)
        }
    }
}