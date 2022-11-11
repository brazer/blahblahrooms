package com.softeq.blahblahrooms.presentation.screens.add

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.softeq.blahblahrooms.R
import com.softeq.blahblahrooms.domain.models.Room
import com.softeq.blahblahrooms.presentation.components.EditRoom
import com.softeq.blahblahrooms.presentation.components.ProgressView
import com.softeq.blahblahrooms.presentation.components.TopBlahBlahRoomsBar
import com.softeq.blahblahrooms.presentation.vm.add.AddRoomSideEffect
import com.softeq.blahblahrooms.presentation.vm.add.AddRoomViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRoomScreen(navController: NavController) {
    val viewModel: AddRoomViewModel = hiltViewModel()
    val state = viewModel.collectAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopBlahBlahRoomsBar(
                stringResource(id = R.string.add_room),
                viewModel::onBackButtonClicked
            )
        }
    ) {

        RoomEditView(
            room = state.value.room,
            modifier = Modifier.padding(it),
            viewModel = viewModel
        )
        if (state.value.isLoading) {
            ProgressView()
        }
    }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is AddRoomSideEffect.ShowError -> {
                Toast.makeText(context, sideEffect.errorMessage, Toast.LENGTH_SHORT).show()
            }
            AddRoomSideEffect.NavigatedBack -> {
                navController.popBackStack()
            }
        }
    }
}

@Composable
private fun RoomEditView(room: Room, modifier: Modifier = Modifier, viewModel: AddRoomViewModel) {
    val state = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxHeight()
            .verticalScroll(state)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        EditRoom(
            room = room,
            editable = true,
            onPriceChanged = viewModel::roomPriceChanged,
            onLocationChanged = viewModel::roomLocationChanged,
            onAddressChanged = viewModel::roomAddressChanged,
            onDescriptionChanged = viewModel::roomDescriptionChanged,
            onPeriodChanged = viewModel::roomPeriodChanged,
            onEmailChanged = viewModel::roomEmailChanged
        )
        Button(
            onClick = viewModel::addRoom
        ) {
            Text(
                text = stringResource(id = R.string.add),
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}