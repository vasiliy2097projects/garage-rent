package ru.garagerent.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import ru.garagerent.domain.Amenity
import ru.garagerent.domain.Garage
import ru.garagerent.domain.GarageRepository
import ru.garagerent.domain.matching

sealed interface SearchUiState {
    data object Loading : SearchUiState

    data class Ready(
        val garages: List<Garage>,
        val selected: Set<Amenity>,
    ) : SearchUiState
}

class SearchViewModel(
    garages: GarageRepository,
) : ViewModel() {
    private val selected = MutableStateFlow<Set<Amenity>>(emptySet())
    private val loaded: StateFlow<List<Garage>?> = garages.observe()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    val ui: StateFlow<SearchUiState> = combine(loaded, selected) { list, picked ->
        if (list == null) {
            SearchUiState.Loading
        } else {
            SearchUiState.Ready(list.matching(picked), picked)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), SearchUiState.Loading)

    fun toggle(amenity: Amenity) {
        selected.update { current ->
            if (amenity in current) current - amenity else current + amenity
        }
    }

    companion object {
        fun factory(garages: GarageRepository): ViewModelProvider.Factory = viewModelFactory {
            initializer { SearchViewModel(garages) }
        }
    }
}
