package com.softeq.blahblahrooms.presentation.screens.roomupdate

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.softeq.blahblahrooms.R
import com.softeq.blahblahrooms.presentation.components.EditRoom
import com.softeq.blahblahrooms.presentation.components.ProgressView
import com.softeq.blahblahrooms.presentation.components.TopBlahBlahRoomsBar
import com.softeq.blahblahrooms.presentation.vm.roomupdate.RoomUpdateSideEffect
import com.softeq.blahblahrooms.presentation.vm.roomupdate.RoomUpdateViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomUpdateScreen(
    navController: NavController,
    roomId: Int
) {
    val roomUpdateViewModel: RoomUpdateViewModel = hiltViewModel()
    val state = roomUpdateViewModel.collectAsState()
    val context = LocalContext.current

    roomUpdateViewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            RoomUpdateSideEffect.BackToPreviousScreen -> {
                navController.popBackStack()
            }
            is RoomUpdateSideEffect.ShowError -> {
                Toast.makeText(
                    context,
                    sideEffect.errorMessage ?: context.getString(R.string.default_error),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    LaunchedEffect(key1 = null, block = {
        roomUpdateViewModel.setRoom(roomId)
    })

    state.value.room?.let { room ->
        Scaffold(
            topBar = { TopBlahBlahRoomsBar(room.address, roomUpdateViewModel::cancelButtonClicked) }
        ) {
            val scrollState = rememberScrollState()
            Column(modifier = Modifier
                .padding(it)
                .fillMaxHeight()
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp, vertical = 8.dp)) {
                EditRoom(
                    room = room,
                    editable = true,
                    onPriceChanged = roomUpdateViewModel::roomPriceChanged,
                    onLocationChanged = roomUpdateViewModel::roomLocationChanged,
                    onAddressChanged = roomUpdateViewModel::roomAddressChanged,
                    onDescriptionChanged = roomUpdateViewModel::roomDescriptionChanged,
                    onPeriodChanged = roomUpdateViewModel::roomPeriodChanged,
                    onEmailChanged = roomUpdateViewModel::roomEmailChanged
                )
                Row {
                    Button(onClick = roomUpdateViewModel::saveButtonClicked,
                        modifier = Modifier.padding(end = 4.dp)) {
                        Text(text = stringResource(id = R.string.save))
                    }
                    Button(onClick = roomUpdateViewModel::cancelButtonClicked) {
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