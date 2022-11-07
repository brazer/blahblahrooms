package com.softeq.blahblahrooms.presentation.screens.roomdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.softeq.blahblahrooms.R
import com.softeq.blahblahrooms.presentation.components.EditRoom
import com.softeq.blahblahrooms.presentation.vm.roomdetails.RoomDetailsSideEffect
import com.softeq.blahblahrooms.presentation.vm.roomdetails.RoomDetailsViewModel
import com.softeq.blahblahrooms.presentation.vm.shared.SharedRoomsViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun RoomDetailsScreen(
    navController: NavController,
    sharedRoomsViewModel: SharedRoomsViewModel,
    roomId: Int
) {
    val scaffoldState = rememberScaffoldState()
    val roomDetailsViewModel: RoomDetailsViewModel = hiltViewModel()
    val sharedState = sharedRoomsViewModel.collectAsState()
    val state = roomDetailsViewModel.collectAsState()

    LaunchedEffect(key1 = null, block = {
        roomDetailsViewModel.setRoom(sharedState.value.rooms, roomId)
    })

    roomDetailsViewModel.collectSideEffect() { sideEffect ->
        when (sideEffect) {
            RoomDetailsSideEffect.BackToPreviousScreen -> {
                navController.popBackStack()
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Column(modifier = Modifier.padding(it)) {
            Button(onClick = {
                roomDetailsViewModel.backButtonClicked()
            }) {
                Text(text = stringResource(id = R.string.back))
            }

            // todo change to immutable content. Wait design
            state.value.room?.let { room ->
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