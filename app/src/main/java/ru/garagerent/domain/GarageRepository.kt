package ru.garagerent.domain

import kotlinx.coroutines.flow.Flow

interface GarageRepository {
    fun observe(): Flow<List<Garage>>
}
