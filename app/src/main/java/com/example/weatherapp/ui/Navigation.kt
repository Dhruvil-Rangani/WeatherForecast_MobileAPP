package com.example.weatherapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.weatherapp.ui.screens.HomeScreen
import com.example.weatherapp.ui.screens.WeatherDetailsScreen
import com.example.weatherapp.viewmodel.WeatherViewModel

@Composable
fun AppNavigation(navController: NavHostController, weatherViewModel: WeatherViewModel) {
    NavHost(navController, startDestination = "home") {
        composable("home") { HomeScreen(navController, weatherViewModel) }
        composable("details/{location}") { backStackEntry ->
            val location = backStackEntry.arguments?.getString("location")
            location?.let { WeatherDetailsScreen(navController, it, weatherViewModel) }
        }
    }
}