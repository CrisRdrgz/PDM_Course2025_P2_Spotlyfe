package com.pdmcourse.spotlyfe.data.repository

import com.pdmcourse.spotlyfe.data.database.PlaceDao
import com.pdmcourse.spotlyfe.data.database.entities.toDomain
import com.pdmcourse.spotlyfe.data.database.entities.toEntity
import com.pdmcourse.spotlyfe.data.model.Place
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class PlaceRepository(private val dao: PlaceDao) {

    fun getAllPlaces(): Flow<List<Place>> {
        return dao.getAllPlaces().map { list -> list.map { it.toDomain() } }
    }

    suspend fun insertPlace(place: Place) {
        dao.insertPlace(place.toEntity())
    }
}
