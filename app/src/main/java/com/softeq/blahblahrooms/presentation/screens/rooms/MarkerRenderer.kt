package com.softeq.blahblahrooms.presentation.screens.rooms

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.softeq.blahblahrooms.R
import com.softeq.blahblahrooms.data.models.RoomMarker

class MarkerRenderer(
    context: Context,
    map: GoogleMap,
    clusterManager: ClusterManager<RoomMarker>
) : DefaultClusterRenderer<RoomMarker>(context, map, clusterManager) {

    private val markerIcon = BitmapDescriptorFactory.fromResource(R.drawable.ic_marker)

    override fun onClusterItemUpdated(item: RoomMarker, marker: Marker) {
        marker.setIcon(markerIcon)
    }

    override fun onBeforeClusterItemRendered(item: RoomMarker, markerOptions: MarkerOptions) {
        markerOptions.icon(markerIcon)
    }

}