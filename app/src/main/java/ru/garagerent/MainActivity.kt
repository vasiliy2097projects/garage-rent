package ru.garagerent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.garagerent.ui.SearchScreen
import ru.garagerent.ui.theme.GarageRentTheme
import ru.garagerent.ui.SearchViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val garages = (application as GarageRentApp).container.garages
        setContent {
            GarageRentTheme {
                val viewModel: SearchViewModel = viewModel(factory = SearchViewModel.factory(garages))
                SearchScreen(viewModel)
            }
        }
    }
}
