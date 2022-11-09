package com.softeq.blahblahrooms.presentation.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.softeq.blahblahrooms.R
import com.softeq.blahblahrooms.presentation.components.TopBlahBlahRoomsBar
import com.softeq.blahblahrooms.presentation.components.ProgressView
import com.softeq.blahblahrooms.presentation.route.NavigationArguments
import com.softeq.blahblahrooms.presentation.route.NavigationRoute
import com.softeq.blahblahrooms.presentation.route.navigateString
import com.softeq.blahblahrooms.presentation.vm.main.MainSideEffect
import com.softeq.blahblahrooms.presentation.vm.main.MainViewModel
import com.softeq.blahblahrooms.presentation.vm.shared.SharedRoomsViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    sharedRoomsViewModel: SharedRoomsViewModel
) {

    val mainViewModel: MainViewModel = hiltViewModel()
    val sharedState = sharedRoomsViewModel.collectAsState()
    val state = mainViewModel.collectAsState()

    LaunchedEffect(key1 = sharedState.value.userRooms, block = {
        mainViewModel.userRoomsChanged(sharedState.value.userRooms)
    })

    mainViewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            MainSideEffect.NavigateToManageRoomsScreen -> {
                navController.navigate(NavigationRoute.ROUTE_MANAGE_ROOMS)
            }
            is MainSideEffect.NavigateToRoomUpdateScreen -> {
                navController.navigate(
                    navigateString(
                        NavigationRoute.ROUTE_ROOM_UPDATE,
                        Pair(NavigationArguments.ARGUMENT_ROOM_ID, sideEffect.roomId)
                    )
                )
            }
        }
    }

    Scaffold(
        topBar = { TopBlahBlahRoomsBar(title = stringResource(id = R.string.home)) }
    ) {
        Column(
            modifier = Modifier.padding(it).fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                navController.navigate(
                    NavigationRoute.ROUTE_ADD_ROOM
                )
            }) {
                Text(text = stringResource(id = R.string.add_room))
            }
            Button(onClick = {
                navController.navigate(NavigationRoute.ROUTE_ROOMS)
            }) {
                Text(stringResource(id = R.string.rooms_flats))
            }
            if (state.value.isManageRoomButtonVisible) {
                Button(onClick = {
                    mainViewModel.manageRoomsButtonClicked()
                }) {
                    Text(text = stringResource(id = R.string.manage_rooms))
                }
            }
        }
        if (sharedState.value.isLoading) {
            ProgressView()
        }
    }
}

@Preview
@Composable
fun PreviewMainScreen() {
    MainScreen(
        navController = NavController(LocalContext.current),
        sharedRoomsViewModel = hiltViewModel()
    )
}