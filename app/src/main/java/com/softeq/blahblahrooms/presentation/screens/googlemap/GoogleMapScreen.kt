package com.softeq.blahblahrooms.presentation.screens.googlemap

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.softeq.blahblahrooms.presentation.ui.BlahBlahRoomsTheme

@Composable
fun GoogleMapScreen(
    navController: NavController
) {
    val singapore = LatLng(1.35, 103.87)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 10f)
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Button(onClick = {
            navController.popBackStack()
        }) {
            Text(text = "Back")
        }
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = MarkerState(position = singapore),
                title = "Singapore",
                snippet = "Marker in Singapore"
            )
        }
    }
}

@Preview
@Composable
fun GoogleMapScreenPreview() {
    BlahBlahRoomsTheme {
        Surface {
            GoogleMapScreen(NavController(LocalContext.current))
        }
    }
}