package com.pdmcourse.spotlyfe.data

import android.content.Context
import com.pdmcourse.spotlyfe.data.repository.PlaceRepository
import com.pdmcourse.spotlyfe.data.database.AppDatabase

class AppProvider(context: Context) {
  private val appDatabase = AppDatabase.getDatabase(context)
  private val placeDao = appDatabase.placeDao()

  private val placeRepository: PlaceRepository = PlaceRepository(placeDao)

  fun providePlaceRepository(): PlaceRepository {
    return placeRepository
  }
}

