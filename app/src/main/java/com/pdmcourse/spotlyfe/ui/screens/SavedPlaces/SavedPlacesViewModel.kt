package com.pdmcourse.spotlyfe.ui.screens.SavedPlaces

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.pdmcourse.spotlyfe.SpotLyfeApplication
import com.pdmcourse.spotlyfe.data.model.Place
import com.pdmcourse.spotlyfe.data.repository.PlaceRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class SavedPlacesViewModel(
    private val placeRepository: PlaceRepository
): ViewModel() {

    val places: StateFlow<List<Place>> = placeRepository.getAllPlaces()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun savePlace(place: Place) {
        viewModelScope.launch {
            placeRepository.insertPlace(place)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as SpotLyfeApplication)
                SavedPlacesViewModel(application.appProvider.providePlaceRepository())
            }
        }
    }
}
