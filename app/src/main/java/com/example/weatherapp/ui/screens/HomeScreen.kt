package com.example.weatherapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.weatherapp.viewmodel.WeatherViewModel

@Composable
fun HomeScreen(navController: NavHostController, weatherViewModel: WeatherViewModel) {
    var location by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val weather by weatherViewModel.weather.collectAsState()
    val error by weatherViewModel.error.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = location,
            onValueChange = { location = it },
            label = { Text("Enter city name") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                if (location.isNotBlank()) {
                    isLoading = true
                    weatherViewModel.fetchWeather(location)
                }
            }),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (location.isNotBlank()) {
                    isLoading = true
                    weatherViewModel.fetchWeather(location)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = location.isNotBlank() && !isLoading
        ) {
            Text("Get Weather")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator()
        }

        error?.let {
            Text(it, color = Color.Red)
        }

        weather?.let {
            isLoading = false
            Text("Location: ${it.location}")
            Text("Temperature: ${it.temperature}°C")
            Text("Description: ${it.description}")

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.navigate("details/${it.location}") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("View Details")
            }
        }
    }
}