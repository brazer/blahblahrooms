package com.softeq.blahblahrooms.presentation.screens.rooms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.softeq.blahblahrooms.data.providers.CurrentLocationProvider
import com.softeq.blahblahrooms.data.providers.getMarkerTitle
import com.softeq.blahblahrooms.data.providers.getSnippet
import com.softeq.blahblahrooms.domain.models.Room

@Composable
fun GoogleMapsContent(
    rooms: List<Room>,
    onMarkerInfoClicked: (id: Int) -> Unit
) {
    val currentLocation = CurrentLocationProvider.location
    val cameraPositionState = if (currentLocation != null) {
        rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(currentLocation, 10f)
        }
    } else rememberCameraPositionState()
    Column(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            rooms.forEach { room ->
                Marker(
                    state = MarkerState(position = room.location),
                    title = room.getMarkerTitle(),
                    snippet = room.getSnippet(),
                    onInfoWindowClick = {
                        onMarkerInfoClicked(room.id)
                    }
                )
            }
        }
    }
}