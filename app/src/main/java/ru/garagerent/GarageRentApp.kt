package ru.garagerent

import android.app.Application

class GarageRentApp : Application() {
    val container: AppContainer by lazy { AppContainer() }
}
