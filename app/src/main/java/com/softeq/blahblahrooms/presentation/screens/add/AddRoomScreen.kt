package com.softeq.blahblahrooms.presentation.screens.add

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.softeq.blahblahrooms.R
import com.softeq.blahblahrooms.domain.models.Room
import com.softeq.blahblahrooms.presentation.components.EditRoom
import com.softeq.blahblahrooms.presentation.vm.add.AddRoomSideEffect
import com.softeq.blahblahrooms.presentation.vm.add.AddRoomState
import com.softeq.blahblahrooms.presentation.vm.add.AddRoomViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun AddRoomScreen(navController: NavController) {
    val viewModel: AddRoomViewModel = hiltViewModel()
    val state = viewModel.collectAsState()
    when (val v = state.value) {
        AddRoomState.AddingOfRoom -> {
            CircularProgressIndicator()
        }
        is AddRoomState.OnRoomChanged -> {
            RoomEditView(room = v.room, viewModel = viewModel, navController = navController)
        }
    }
    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            AddRoomSideEffect.RoomIsAdded -> {
                navController.popBackStack()
            }
            is AddRoomSideEffect.Toast -> {
                Toast.makeText(sideEffect.context, sideEffect.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
private fun RoomEditView(navController: NavController, room: Room, viewModel: AddRoomViewModel) {
    Column(modifier = Modifier.fillMaxHeight()) {
        Button(onClick = {
            navController.popBackStack()
        }) {
            Text(text = stringResource(id = R.string.back))
        }
        EditRoom(
            room = room,
            onPriceChanged = viewModel::roomPriceChanged,
            onLocationChanged = viewModel::roomLocationChanged,
            onAddressChanged = viewModel::roomAddressChanged,
            onDescriptionChanged = viewModel::roomDescriptionChanged,
            onPeriodChanged = viewModel::roomPeriodChanged,
            onEmailChanged = viewModel::roomEmailChanged
        )
        Button(onClick = viewModel::addRoom) {
            Text(text = stringResource(id = R.string.add))
        }
    }
}