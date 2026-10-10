package ru.garagerent.data

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.garagerent.domain.Amenity
import ru.garagerent.domain.Garage
import ru.garagerent.domain.GarageRepository
import ru.garagerent.domain.GarageStatus

const val MOCK_DELAY_MILLIS = 800L

internal val mockGarages = listOf(
    Garage(
        id = "warm",
        title = "Теплый гараж с ямой",
        address = "2-й Гаражный проезд, 7",
        pricePerHour = 350,
        amenities = setOf(Amenity.INSPECTION_PIT, Amenity.COMPRESSOR),
        pinX = 0.30f,
        pinY = 0.22f,
        status = GarageStatus.APPROVED,
    ),
    Garage(
        id = "lift",
        title = "Бокс с подъемником",
        address = "ул. Станционная, 14",
        pricePerHour = 480,
        amenities = setOf(Amenity.CAR_LIFT, Amenity.HEATING),
        pinX = 0.68f,
        pinY = 0.18f,
        status = GarageStatus.APPROVED,
    ),
    Garage(
        id = "power",
        title = "Бокс на 380V",
        address = "Автомоторный проезд, 3",
        pricePerHour = 520,
        amenities = setOf(Amenity.POWER_380V, Amenity.HEATING),
        pinX = 0.46f,
        pinY = 0.36f,
        status = GarageStatus.APPROVED,
    ),
    Garage(
        id = "pit",
        title = "Яма у депо",
        address = "ул. Заводская, 21",
        pricePerHour = 300,
        amenities = setOf(Amenity.INSPECTION_PIT),
        pinX = 0.22f,
        pinY = 0.40f,
        status = GarageStatus.APPROVED,
    ),
    Garage(
        id = "air",
        title = "Компрессор и подъемник",
        address = "ш. Энтузиастов, 40",
        pricePerHour = 610,
        amenities = setOf(Amenity.COMPRESSOR, Amenity.CAR_LIFT),
        pinX = 0.74f,
        pinY = 0.34f,
        status = GarageStatus.APPROVED,
    ),
    Garage(
        id = "draft",
        title = "Черновик без допуска",
        address = "ул. Складская, 1",
        pricePerHour = 200,
        amenities = Amenity.entries.toSet(),
        pinX = 0.50f,
        pinY = 0.50f,
        status = GarageStatus.PENDING,
    ),
)

class FakeGarageRepository : GarageRepository {
    override fun observe(): Flow<List<Garage>> = flow {
        delay(MOCK_DELAY_MILLIS)
        emit(mockGarages)
    }
}
