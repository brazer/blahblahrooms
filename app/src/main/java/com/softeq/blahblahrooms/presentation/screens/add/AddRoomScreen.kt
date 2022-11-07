package com.softeq.blahblahrooms.presentation.screens.add

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.softeq.blahblahrooms.R
import com.softeq.blahblahrooms.domain.models.Room
import com.softeq.blahblahrooms.presentation.components.EditRoom
import com.softeq.blahblahrooms.presentation.components.ProgressView
import com.softeq.blahblahrooms.presentation.vm.add.AddRoomSideEffect
import com.softeq.blahblahrooms.presentation.vm.add.AddRoomViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun AddRoomScreen(navController: NavController) {
    val viewModel: AddRoomViewModel = hiltViewModel()
    val state = viewModel.collectAsState()
    RoomEditView(room = state.value.room, viewModel = viewModel)
    if (state.value.progress) {
        ProgressView()
    }
    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            AddRoomSideEffect.RoomIsAdded -> {
                navController.popBackStack()
            }
            is AddRoomSideEffect.Toast -> {
                Toast.makeText(sideEffect.context, sideEffect.message, Toast.LENGTH_SHORT).show()
            }
            AddRoomSideEffect.NavigatedBack -> {
                navController.popBackStack()
            }
        }
    }
}

@Composable
private fun RoomEditView(room: Room, viewModel: AddRoomViewModel) {
    Column(modifier = Modifier.fillMaxHeight()) {
        Button(onClick = viewModel::onBackButtonClicked) {
            Text(text = stringResource(id = R.string.back))
        }
        EditRoom(
            room = room,
            onPriceChanged = viewModel::onPriceChanged,
            onLocationChanged = viewModel::onLocationChanged,
            onAddressChanged = viewModel::onAddressChanged,
            onDescriptionChanged = viewModel::onDescriptionChanged,
            onPeriodChanged = viewModel::onPeriodChanged,
            onEmailChanged = viewModel::onEmailChanged
        )
        Button(onClick = viewModel::addRoom) {
            Text(text = stringResource(id = R.string.add))
        }
    }
}