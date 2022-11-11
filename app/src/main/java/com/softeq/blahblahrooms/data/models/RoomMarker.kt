package com.softeq.blahblahrooms.data.models

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import com.softeq.blahblahrooms.data.providers.getMarkerTitle
import com.softeq.blahblahrooms.data.providers.getSnippet
import com.softeq.blahblahrooms.domain.models.Room

data class RoomMarker(val room: Room): ClusterItem {

    override fun getPosition(): LatLng {
        return room.location
    }

    override fun getTitle(): String {
        return room.getMarkerTitle()
    }

    override fun getSnippet(): String {
        return room.getSnippet()
    }

}
