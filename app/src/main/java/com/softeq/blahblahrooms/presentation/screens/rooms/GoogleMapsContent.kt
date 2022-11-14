package com.softeq.blahblahrooms.presentation.screens.rooms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.ClusterManager.OnClusterClickListener
import com.google.maps.android.clustering.ClusterManager.OnClusterItemInfoWindowClickListener
import com.google.maps.android.compose.*
import com.softeq.blahblahrooms.data.DataConfig
import com.softeq.blahblahrooms.data.models.RoomMarker
import com.softeq.blahblahrooms.data.providers.CurrentLocationProvider

@OptIn(MapsComposeExperimentalApi::class)
@Composable
fun GoogleMapsContent(
    roomMarkers: List<RoomMarker>,
    onMarkerInfoClicked: (id: Int) -> Unit
) {
    val currentLocation = CurrentLocationProvider.location
    val cameraPositionState = if (currentLocation != null) {
        rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(currentLocation, 10f)
        }
    } else rememberCameraPositionState()
    val mapProperties = remember {
        MapProperties(
            isBuildingEnabled = true,
            isMyLocationEnabled = true
        )
    }
    Column(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = mapProperties,
            googleMapOptionsFactory = {
                GoogleMapOptions().mapId(DataConfig.MAP_ID)
            }
        ) {
            val context = LocalContext.current
            var clusterManager by remember {
                mutableStateOf<ClusterManager<RoomMarker>?>(null)
            }
            MapEffect(roomMarkers) { map ->
                map.clear() //hotfix: there is a marker on (0.0, 0.0) location
                if (clusterManager == null) {
                    clusterManager = ClusterManager(context, map)
                }
                val renderer = MarkerRenderer(context, map, checkNotNull(clusterManager))
                val clusterItemInfoWindowClickListener = OnClusterItemInfoWindowClickListener<RoomMarker> {
                    onMarkerInfoClicked(it.room.id)
                }
                val clusterClickListener = OnClusterClickListener<RoomMarker> { cluster ->
                    val builder = LatLngBounds.builder()
                    cluster.items.forEach { marker ->
                        builder.include(marker.position)
                    }
                    val bounds = builder.build()
                    val camUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 250)
                    map.animateCamera(camUpdate)
                    true
                }
                clusterManager?.renderer = renderer
                clusterManager?.addItems(roomMarkers)
                clusterManager?.setOnClusterClickListener(clusterClickListener)
                clusterManager?.setOnClusterItemInfoWindowClickListener(clusterItemInfoWindowClickListener)
                map.setOnCameraIdleListener(clusterManager)
            }
        }
    }
}