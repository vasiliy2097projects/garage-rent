package ru.garagerent

import ru.garagerent.data.FakeGarageRepository
import ru.garagerent.domain.GarageRepository

class AppContainer(
    val garages: GarageRepository = FakeGarageRepository(),
)
