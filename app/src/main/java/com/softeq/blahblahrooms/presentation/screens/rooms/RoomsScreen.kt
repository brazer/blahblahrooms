package com.softeq.blahblahrooms.presentation.screens.rooms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.softeq.blahblahrooms.R
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
    val pagerState = remember { mutableStateOf(0) }
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
        topBar = { TopBlahBlahRoomsBar(stringResource(R.string.rooms_flats), roomsViewModel::backButtonClicked) }
    ) {
        Column(modifier = Modifier.padding(it)) {
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
                    GoogleMapsContent(state.value.rooms) { roomId ->
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