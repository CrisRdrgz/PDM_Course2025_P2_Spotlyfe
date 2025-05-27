package com.pdmcourse.spotlyfe.ui.screens.SavedPlaces

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.android.gms.maps.model.LatLng
import com.pdmcourse.spotlyfe.data.model.Place
import com.pdmcourse.spotlyfe.ui.layout.CustomTopBar
import com.pdmcourse.spotlyfe.ui.navigation.SavedPlacesScreenNavigation

@Composable
fun AddPlaceScreen(
    navController: NavController,
    viewModel: SavedPlacesViewModel = viewModel(factory = SavedPlacesViewModel.Factory)
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedLatLng by remember { mutableStateOf(LatLng(13.6790, -89.2357)) }

    Scaffold(
        topBar = { CustomTopBar(title = "New Place") { navController.popBackStack() } }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )

            SelectLocationMap(
                initialPosition = selectedLatLng,
                onLocationChanged = { selectedLatLng = it }
            )

            Button(
                onClick = {
                    if (name.isNotBlank()) {
                        val place = Place(
                            name = name,
                            remark = description,
                            latitude = selectedLatLng.latitude,
                            longitude = selectedLatLng.longitude
                        )
                        viewModel.savePlace(place)
                        navController.navigate(SavedPlacesScreenNavigation)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save")
            }
        }
    }
}

