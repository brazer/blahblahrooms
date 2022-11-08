package com.softeq.blahblahrooms.presentation.screens.roomdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.softeq.blahblahrooms.presentation.components.EditRoom
import com.softeq.blahblahrooms.presentation.components.TopBlahBlahRoomsBar
import com.softeq.blahblahrooms.presentation.vm.roomdetails.RoomDetailsSideEffect
import com.softeq.blahblahrooms.presentation.vm.roomdetails.RoomDetailsViewModel
import com.softeq.blahblahrooms.presentation.vm.shared.SharedRoomsViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomDetailsScreen(
    navController: NavController,
    sharedRoomsViewModel: SharedRoomsViewModel,
    roomId: Int
) {
    val roomDetailsViewModel: RoomDetailsViewModel = hiltViewModel()
    val sharedState = sharedRoomsViewModel.collectAsState()
    val state = roomDetailsViewModel.collectAsState()

    LaunchedEffect(key1 = null, block = {
        roomDetailsViewModel.setRoom(sharedState.value.rooms, roomId)
    })

    roomDetailsViewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            RoomDetailsSideEffect.BackToPreviousScreen -> {
                navController.popBackStack()
            }
        }
    }

    // todo change to immutable content. Wait design
    state.value.room?.let { room ->
        Scaffold(
            topBar = { TopBlahBlahRoomsBar(room.address, roomDetailsViewModel::backButtonClicked) }
        ) {
            Column(modifier = Modifier.padding(it)) {
                EditRoom(
                    room = room,
                    onPriceChanged = {},
                    onLocationChanged = {},
                    onAddressChanged = {},
                    onDescriptionChanged = {},
                    onPeriodChanged = {},
                    onEmailChanged = {}
                )
            }
        }
    }
}