package com.example.sevenwindsstudio.presentation.navigation

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Register : Screen("register")
    data object Login : Screen("login")
    data object Locations : Screen("locations")
    data object Map : Screen("map")
    data object MapWithSelectedLocation : Screen("map/{locationId}") {
        fun createRoute(locationId: Int) = "map/$locationId"
    }
    data object Menu : Screen("menu/{locationId}") {
        fun createRoute(locationId: Int) = "menu/$locationId"
    }
    data object Order : Screen("order")
}
