package com.softeq.blahblahrooms.presentation.screens.roomupdate

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.softeq.blahblahrooms.R
import com.softeq.blahblahrooms.presentation.components.EditRoom
import com.softeq.blahblahrooms.presentation.components.ProgressView
import com.softeq.blahblahrooms.presentation.components.TopBlahBlahRoomsBar
import com.softeq.blahblahrooms.presentation.vm.roomupdate.RoomUpdateSideEffect
import com.softeq.blahblahrooms.presentation.vm.roomupdate.RoomUpdateViewModel
import com.softeq.blahblahrooms.presentation.vm.shared.SharedRoomsViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomUpdateScreen(
    navController: NavController,
    sharedRoomsViewModel: SharedRoomsViewModel,
    roomId: Int
) {
    val sharedState = sharedRoomsViewModel.collectAsState()
    val roomUpdateViewModel: RoomUpdateViewModel = hiltViewModel()
    val state = roomUpdateViewModel.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = null, block = {
        roomUpdateViewModel.setRoom(sharedState.value.userRooms, roomId)
    })

    roomUpdateViewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            RoomUpdateSideEffect.BackToPreviousScreen -> {
                navController.popBackStack()
            }
            is RoomUpdateSideEffect.ShowError -> {
                Toast.makeText(context, sideEffect.errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    state.value.room?.let { room ->
        Scaffold(
            topBar = { TopBlahBlahRoomsBar(room.address, roomUpdateViewModel::cancelButtonClicked) }
        ) {
            Column(modifier = Modifier.padding(it)) {
                EditRoom(
                    room = room,
                    onPriceChanged = roomUpdateViewModel::roomPriceChanged,
                    onLocationChanged = roomUpdateViewModel::roomLocationChanged,
                    onAddressChanged = roomUpdateViewModel::roomAddressChanged,
                    onDescriptionChanged = roomUpdateViewModel::roomDescriptionChanged,
                    onPeriodChanged = roomUpdateViewModel::roomPeriodChanged,
                    onEmailChanged = roomUpdateViewModel::roomEmailChanged
                )
                Row {
                    Button(onClick = {
                        roomUpdateViewModel.saveButtonClicked()
                    }) {
                        Text(text = stringResource(id = R.string.save))
                    }
                    Button(onClick = {
                        roomUpdateViewModel.cancelButtonClicked()
                    }) {
                        Text(text = stringResource(id = R.string.cancel))
                    }
                }
            }
            if (state.value.isLoading) {
                ProgressView()
            }
        }
    }
}