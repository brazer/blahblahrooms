package com.softeq.blahblahrooms.presentation.screens.managerooms

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.softeq.blahblahrooms.presentation.components.RoomTile
import com.softeq.blahblahrooms.presentation.route.NavigationArguments
import com.softeq.blahblahrooms.presentation.route.NavigationRoute
import com.softeq.blahblahrooms.presentation.route.navigateString
import com.softeq.blahblahrooms.presentation.vm.managerooms.ManageRoomsSideEffect
import com.softeq.blahblahrooms.presentation.vm.managerooms.ManageRoomsViewModel
import com.softeq.blahblahrooms.presentation.vm.shared.SharedRoomsViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun ManageRoomsScreen(
    navController: NavController,
    sharedRoomsViewModel: SharedRoomsViewModel
) {
    val sharedState = sharedRoomsViewModel.collectAsState()
    val manageRoomsViewModel: ManageRoomsViewModel = hiltViewModel()
    val state = manageRoomsViewModel.collectAsState()
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = null, block = {
        manageRoomsViewModel.setRooms(sharedState.value.userRooms)
    })

    manageRoomsViewModel.collectSideEffect() { sideEffect ->
        when (sideEffect) {
            is ManageRoomsSideEffect.NavigateToUpdateRoomScreen -> {
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
        scaffoldState = scaffoldState
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            items(state.value.rooms) { room ->
                RoomTile(room = room, onClick = { id ->
                    manageRoomsViewModel.roomClicked(id)
                })
            }
        }
    }

}