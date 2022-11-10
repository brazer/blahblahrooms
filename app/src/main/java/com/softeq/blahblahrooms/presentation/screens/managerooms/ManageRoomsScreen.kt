package com.softeq.blahblahrooms.presentation.screens.managerooms

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.softeq.blahblahrooms.R
import com.softeq.blahblahrooms.presentation.components.RoomTile
import com.softeq.blahblahrooms.presentation.components.TopBlahBlahRoomsBar
import com.softeq.blahblahrooms.presentation.route.NavigationArguments
import com.softeq.blahblahrooms.presentation.route.NavigationRoute
import com.softeq.blahblahrooms.presentation.route.navigateString
import com.softeq.blahblahrooms.presentation.vm.managerooms.ManageRoomsSideEffect
import com.softeq.blahblahrooms.presentation.vm.managerooms.ManageRoomsViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageRoomsScreen(
    navController: NavController
) {
    val manageRoomsViewModel: ManageRoomsViewModel = hiltViewModel()
    val state = manageRoomsViewModel.collectAsState()

    manageRoomsViewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is ManageRoomsSideEffect.NavigateToUpdateRoomScreen -> {
                navController.navigate(
                    navigateString(
                        NavigationRoute.ROUTE_ROOM_UPDATE,
                        Pair(NavigationArguments.ARGUMENT_ROOM_ID, sideEffect.roomId)
                    )
                )
            }
            ManageRoomsSideEffect.BackToPreviousScreen -> navController.popBackStack()
        }
    }

    Scaffold(
        topBar = {
            TopBlahBlahRoomsBar(
                title = stringResource(id = R.string.manage_rooms),
                manageRoomsViewModel::onBackButtonClicked
            )
        }
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            items(state.value.rooms) { room ->
                RoomTile(room = room, onClick = manageRoomsViewModel::roomClicked)
            }
        }
    }

}