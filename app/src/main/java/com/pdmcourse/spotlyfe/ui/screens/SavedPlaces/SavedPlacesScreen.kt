package com.pdmcourse.spotlyfe.ui.screens.SavedPlaces

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.pdmcourse.spotlyfe.ui.layout.CustomFloatingButton
import com.pdmcourse.spotlyfe.ui.layout.CustomTopBar
import com.pdmcourse.spotlyfe.ui.navigation.AddPlaceScreenNavigation
import androidx.navigation.NavController


@Composable
fun SavedPlacesScreen(
  navController: NavController,
  viewModel: SavedPlacesViewModel = viewModel(factory = SavedPlacesViewModel.Factory)
) {
  val places by viewModel.places.collectAsState()

  val cameraPositionState = rememberCameraPositionState {
    position = if (places.isNotEmpty()) {
      CameraPosition.fromLatLngZoom(LatLng(places[0].latitude, places[0].longitude), 16f)
    } else {
      CameraPosition.fromLatLngZoom(LatLng(13.6790, -89.2357), 14f)
    }
  }

  var uiSettings by remember {
    mutableStateOf(MapUiSettings(zoomControlsEnabled = false))
  }
  var properties by remember {
    mutableStateOf(MapProperties(mapType = MapType.HYBRID))
  }

  Scaffold(
    topBar = { CustomTopBar() },
    floatingActionButton = {
      CustomFloatingButton(
        onClick = {
          navController.navigate(AddPlaceScreenNavigation)
        }
      )
    }
  ) { innerPadding ->
    Column(modifier = Modifier.padding(innerPadding)) {
      GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = properties,
        uiSettings = uiSettings
      ) {
        places.forEach { place ->
          Marker(
            state = MarkerState(position = LatLng(place.latitude, place.longitude)),
            title = place.name,
            snippet = place.remark
          )
        }
      }
    }
  }
}
