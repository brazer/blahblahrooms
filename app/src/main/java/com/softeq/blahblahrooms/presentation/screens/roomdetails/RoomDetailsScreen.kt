package com.softeq.blahblahrooms.presentation.screens.roomdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.softeq.blahblahrooms.presentation.components.EditRoom
import com.softeq.blahblahrooms.presentation.components.TopBlahBlahRoomsBar
import com.softeq.blahblahrooms.presentation.vm.roomdetails.RoomDetailsSideEffect
import com.softeq.blahblahrooms.presentation.vm.roomdetails.RoomDetailsViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomDetailsScreen(
    navController: NavController,
    roomId: Int
) {
    val roomDetailsViewModel: RoomDetailsViewModel = hiltViewModel()
    val state = roomDetailsViewModel.collectAsState()

    roomDetailsViewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            RoomDetailsSideEffect.BackToPreviousScreen -> {
                navController.popBackStack()
            }
        }
    }

    LaunchedEffect(key1 = null, block = {
        roomDetailsViewModel.setRoom(roomId)
    })

    state.value.room?.let { room ->
        Scaffold(
            topBar = { TopBlahBlahRoomsBar(room.address, roomDetailsViewModel::backButtonClicked) }
        ) {
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxHeight()
                    .verticalScroll(scrollState)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                EditRoom(
                    room = room,
                    editable = false,
                    onPriceChanged = {},
                    onLocationChanged = {},
                    onAddressChanged = {},
                    onCityChanged = {},
                    onDescriptionChanged = {},
                    onPeriodChanged = {},
                    onEmailChanged = {}
                )
            }
        }
    }
}