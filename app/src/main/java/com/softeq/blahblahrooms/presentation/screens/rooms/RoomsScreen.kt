package com.softeq.blahblahrooms.presentation.screens.rooms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.softeq.blahblahrooms.R
import com.softeq.blahblahrooms.data.providers.CurrentLocationProvider
import com.softeq.blahblahrooms.data.providers.getMarkerTitle
import com.softeq.blahblahrooms.data.providers.getSnippet
import com.softeq.blahblahrooms.presentation.components.ProgressView
import com.softeq.blahblahrooms.presentation.vm.rooms.RoomsViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun RoomsScreen(navController: NavController) {
    val currentLocation = CurrentLocationProvider.location
    val cameraPositionState = if (currentLocation != null) {
        rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(currentLocation, 10f)
        }
    } else rememberCameraPositionState()
    val viewModel: RoomsViewModel = hiltViewModel()
    val state = viewModel.collectAsState()
    Column(modifier = Modifier.fillMaxSize()) {
        Button(onClick = {
            navController.popBackStack()
        }) {
            Text(text = stringResource(id = R.string.back))
        }
        if (state.value.progress) {
            viewModel.loadData()
            ProgressView()
        } else {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            ) {
                state.value.rooms.forEach {
                    Marker(
                        state = MarkerState(position = it.location),
                        title = it.getMarkerTitle(),
                        snippet = it.getSnippet()
                    )
                }
            }
        }
    }
}