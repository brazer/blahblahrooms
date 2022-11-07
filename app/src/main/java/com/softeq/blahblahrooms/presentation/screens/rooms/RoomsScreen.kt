package com.softeq.blahblahrooms.presentation.screens.rooms

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.softeq.blahblahrooms.R
import com.softeq.blahblahrooms.presentation.components.ProgressView
import com.softeq.blahblahrooms.presentation.route.NavigationArguments
import com.softeq.blahblahrooms.presentation.route.NavigationRoute
import com.softeq.blahblahrooms.presentation.route.navigateString
import com.softeq.blahblahrooms.presentation.vm.rooms.RoomsSideEffect
import com.softeq.blahblahrooms.presentation.vm.rooms.RoomsViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun RoomsScreen(navController: NavController) {
    val pagerState = remember { mutableStateOf(0) }
    val pages = listOf(
        stringResource(id = R.string.list),
        stringResource(id = R.string.map),
    )

    val roomsViewModel: RoomsViewModel = hiltViewModel()
    val state = roomsViewModel.collectAsState()

    roomsViewModel.collectSideEffect() { sideEffect ->
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

    Column {
        Button(onClick = {
            roomsViewModel.backButtonClicked()
        }) {
            Text(text = stringResource(id = R.string.back))
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
                ListContent(state.value.rooms) {
                    roomsViewModel.roomClicked(it)
                }
            }
            1 -> {
                GoogleMapsContent(state.value.rooms) {
                    roomsViewModel.roomClicked(it)
                }
            }
        }
    }
    if (state.value.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ProgressView()
        }
    }
}