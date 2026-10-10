package ru.garagerent.domain

enum class Amenity {
    INSPECTION_PIT,
    CAR_LIFT,
    HEATING,
    POWER_380V,
    COMPRESSOR,
}

enum class GarageStatus {
    APPROVED,
    PENDING,
}

data class Garage(
    val id: String,
    val title: String,
    val address: String,
    val pricePerHour: Int,
    val amenities: Set<Amenity>,
    val pinX: Float,
    val pinY: Float,
    val status: GarageStatus,
)

fun List<Garage>.matching(required: Set<Amenity>): List<Garage> = filter { garage ->
    garage.status == GarageStatus.APPROVED && required.all { it in garage.amenities }
}
