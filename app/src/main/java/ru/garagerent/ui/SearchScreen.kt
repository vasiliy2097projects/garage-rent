package ru.garagerent.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.garagerent.R
import ru.garagerent.domain.Amenity
import ru.garagerent.domain.Garage
import ru.garagerent.ui.theme.Accent
import ru.garagerent.ui.theme.ChipBorder
import ru.garagerent.ui.theme.Ink
import ru.garagerent.ui.theme.Muted
import ru.garagerent.ui.theme.Paper
import ru.garagerent.ui.theme.Pin
import ru.garagerent.ui.theme.SearchIndicator
import ru.garagerent.ui.theme.Sheet

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SearchScreen(viewModel: SearchViewModel) {
    val state by viewModel.ui.collectAsStateWithLifecycle()
    var filtersOpen by rememberSaveable { mutableStateOf(false) }
    val selected = (state as? SearchUiState.Ready)?.selected.orEmpty()

    Scaffold(
        containerColor = Paper,
        bottomBar = { SearchBottomBar() },
    ) { padding ->
        BottomSheetScaffold(
            modifier = Modifier.padding(padding),
            containerColor = Paper,
            sheetContainerColor = Sheet,
            sheetPeekHeight = 120.dp,
            sheetDragHandle = { BottomSheetDefaults.DragHandle() },
            sheetContent = { GarageSheet(state) },
        ) {
            Box(Modifier.fillMaxSize()) {
                MockMap(
                    garages = (state as? SearchUiState.Ready)?.garages.orEmpty(),
                    modifier = Modifier.fillMaxSize(),
                )
                FilterButton(
                    onClick = { filtersOpen = true },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp),
                )
            }
        }
    }

    if (filtersOpen) {
        ModalBottomSheet(
            onDismissRequest = { filtersOpen = false },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            containerColor = Sheet,
        ) {
            Column(
                Modifier
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 28.dp),
            ) {
                Text(
                    text = stringResource(R.string.filters),
                    style = MaterialTheme.typography.titleMedium,
                    color = Ink,
                )
                Spacer(Modifier.size(12.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Amenity.entries.forEach { amenity ->
                        val on = amenity in selected
                        FilterChip(
                            selected = on,
                            onClick = { viewModel.toggle(amenity) },
                            label = { Text(stringResource(amenity.label)) },
                            colors = FilterChipDefaults.filterChipColors(
                                containerColor = Sheet,
                                labelColor = Ink,
                                selectedContainerColor = Accent,
                                selectedLabelColor = Sheet,
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = on,
                                borderColor = ChipBorder,
                                selectedBorderColor = Accent,
                            ),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun GarageSheet(state: SearchUiState) {
    when (state) {
        SearchUiState.Loading -> {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(18.dp),
                    strokeWidth = 2.dp,
                    color = Accent,
                )
                Spacer(Modifier.width(10.dp))
                Text(stringResource(R.string.loading), color = Ink)
            }
        }

        is SearchUiState.Ready -> {
            if (state.garages.isEmpty()) {
                Text(
                    text = stringResource(R.string.empty_search),
                    color = Ink,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp),
                )
            } else {
                LazyColumn(Modifier.fillMaxWidth().heightIn(max = 420.dp)) {
                    items(state.garages, key = { it.id }) { garage ->
                        GarageRow(garage)
                    }
                }
            }
        }
    }
}

@Composable
private fun GarageRow(garage: Garage) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp),
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = garage.title,
                color = Ink,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f),
            )
            Spacer(Modifier.width(12.dp))
            Text(
                text = stringResource(R.string.price_rub, garage.pricePerHour),
                color = Accent,
                fontWeight = FontWeight.SemiBold,
            )
        }
        Text(
            text = garage.address,
            color = Ink.copy(alpha = 0.6f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
private fun SearchBottomBar() {
    NavigationBar(containerColor = Sheet) {
        NavigationBarItem(
            selected = true,
            onClick = {},
            icon = { Icon(Icons.Filled.Search, contentDescription = stringResource(R.string.tab_search)) },
            label = { Text(stringResource(R.string.tab_search)) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Pin,
                selectedTextColor = Pin,
                indicatorColor = SearchIndicator,
            ),
        )
        NavigationBarItem(
            selected = false,
            enabled = false,
            onClick = {},
            icon = { Icon(Icons.Filled.DateRange, contentDescription = stringResource(R.string.tab_bookings)) },
            label = { Text(stringResource(R.string.tab_bookings)) },
            colors = NavigationBarItemDefaults.colors(
                disabledIconColor = Muted,
                disabledTextColor = Muted,
            ),
        )
        NavigationBarItem(
            selected = false,
            enabled = false,
            onClick = {},
            icon = { Icon(Icons.Filled.Person, contentDescription = stringResource(R.string.tab_profile)) },
            label = { Text(stringResource(R.string.tab_profile)) },
            colors = NavigationBarItemDefaults.colors(
                disabledIconColor = Muted,
                disabledTextColor = Muted,
            ),
        )
    }
}

private val Amenity.label: Int
    get() = when (this) {
        Amenity.INSPECTION_PIT -> R.string.amenity_pit
        Amenity.CAR_LIFT -> R.string.amenity_lift
        Amenity.HEATING -> R.string.amenity_heating
        Amenity.POWER_380V -> R.string.amenity_power
        Amenity.COMPRESSOR -> R.string.amenity_compressor
    }
