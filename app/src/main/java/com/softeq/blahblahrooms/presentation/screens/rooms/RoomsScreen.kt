package com.softeq.blahblahrooms.presentation.screens.rooms

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.softeq.blahblahrooms.R
import com.softeq.blahblahrooms.data.mappers.map
import com.softeq.blahblahrooms.data.models.Period
import com.softeq.blahblahrooms.presentation.components.ProgressView
import com.softeq.blahblahrooms.presentation.components.TopBlahBlahRoomsBar
import com.softeq.blahblahrooms.presentation.route.NavigationArguments
import com.softeq.blahblahrooms.presentation.route.NavigationRoute
import com.softeq.blahblahrooms.presentation.route.navigateString
import com.softeq.blahblahrooms.presentation.vm.rooms.RoomsSideEffect
import com.softeq.blahblahrooms.presentation.vm.rooms.RoomsViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomsScreen(navController: NavController) {
    val pagerState = remember { mutableStateOf(1) }
    val pages = listOf(
        stringResource(id = R.string.list),
        stringResource(id = R.string.map),
    )

    val roomsViewModel: RoomsViewModel = hiltViewModel()
    val state = roomsViewModel.collectAsState()

    roomsViewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            RoomsSideEffect.BackToPreviousScreen -> {
                navController.popBackStack()
            }
            is RoomsSideEffect.NavigateToRoomDetailsScreen -> {
                navController.navigate(
                    navigateString(
                        NavigationRoute.ROUTE_ROOM_DETAILS,
                        Pair(NavigationArguments.ARGUMENT_ROOM_ID, sideEffect.roomId)
                    )
                )
            }
        }
    }

    Scaffold(
        topBar = {
            TopBlahBlahRoomsBar(
                stringResource(R.string.rooms_flats),
                roomsViewModel::backButtonClicked,
                actions = {
                    Button(onClick = { roomsViewModel.filtersButtonClicked() }) {
                        Text(text = stringResource(id = R.string.filters))
                    }
                }
            )
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            Row {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {
                    DropdownMenu(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        expanded = state.value.isVisibleFilters,
                        onDismissRequest = { roomsViewModel.dismissFiltersRequest() }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.period) + ":",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Spacer(modifier = Modifier.width(40.dp))
                            Checkbox(
                                checked = state.value.period == Period.SHORT,
                                onCheckedChange = {
                                    roomsViewModel.periodShortClicked()
                                })
                            Text(
                                text = stringResource(R.string.period_short),
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Checkbox(
                                checked = state.value.period == Period.LONG,
                                onCheckedChange = {
                                    roomsViewModel.periodLongClicked()
                                })
                            Text(
                                text = stringResource(R.string.period_long),
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Checkbox(checked = state.value.period == null, onCheckedChange = {
                                roomsViewModel.periodBothClicked()
                            })
                            Text(
                                text = stringResource(R.string.both),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                value = state.value.city ?: "",
                                onValueChange = { text ->
                                    roomsViewModel.cityFilterChanged(text)
                                },
                                label = {
                                    Text(
                                        text = stringResource(R.string.city) + ":",
                                        style = MaterialTheme.typography.labelMedium
                                    )
                                })
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                modifier = Modifier.weight(1f),
                                value = state.value.minPrice?.toString() ?: 0.0.toString(),
                                onValueChange = { text ->
                                    roomsViewModel.minPriceFilterChanged(text.toDoubleOrNull())
                                },
                                label = {
                                    Text(
                                        modifier = Modifier.weight(1f),
                                        text = stringResource(R.string.minPrice) + ":",
                                        style = MaterialTheme.typography.labelMedium
                                    )
                                })
                            Spacer(modifier = Modifier.width(10.dp))
                            OutlinedTextField(
                                modifier = Modifier.weight(1f),
                                value = state.value.maxPrice?.toString() ?: 0.0.toString(),
                                onValueChange = { text ->
                                    roomsViewModel.maxPriceFilterChanged(text.toDoubleOrNull())
                                },
                                label = {
                                    Text(
                                        modifier = Modifier.weight(1f),
                                        text = stringResource(R.string.maxPrice) + ":",
                                        style = MaterialTheme.typography.labelMedium
                                    )
                                })
                        }
                        Button(onClick = { roomsViewModel.applyButtonClicked() }) {
                            Text(text = stringResource(id = R.string.apply))
                        }
                    }
                }
            }

            TabRow(selectedTabIndex = pagerState.value) {
                pages.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = pagerState.value == index,
                        onClick = { pagerState.value = index }
                    )
                }
            }

            when (pagerState.value) {
                0 -> {
                    ListContent(state.value.rooms) { roomId ->
                        roomsViewModel.roomClicked(roomId)
                    }
                }
                1 -> {
                    GoogleMapsContent(state.value.rooms.map()) { roomId ->
                        roomsViewModel.roomClicked(roomId)
                    }
                }
            }
        }
        if (state.value.isLoading) {
            ProgressView()
        }
    }
}