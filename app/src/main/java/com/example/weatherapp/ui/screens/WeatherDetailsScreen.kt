package com.example.weatherapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.weatherapp.viewmodel.WeatherViewModel

@Composable
fun WeatherDetailsScreen(
    navController: NavHostController,
    location: String,
    weatherViewModel: WeatherViewModel
) {
    var isLoading by remember { mutableStateOf(true) }

    val weather by weatherViewModel.weather.collectAsState()
    val error by weatherViewModel.error.collectAsState()

    LaunchedEffect(location) {
        weatherViewModel.fetchWeather(location)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Weather Details for $location",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator()
        }

        error?.let {
            Text(it, color = Color.Red)
            isLoading = false
        }

        weather?.let {
            isLoading = false
            Text("Temperature: ${it.temperature}°C", style = MaterialTheme.typography.bodyLarge)
            Text("Description: ${it.description}", style = MaterialTheme.typography.bodyLarge)
            // Add more weather details here as needed
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back to Home")
        }
    }
}