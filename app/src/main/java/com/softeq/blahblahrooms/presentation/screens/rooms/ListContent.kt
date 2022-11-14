package com.softeq.blahblahrooms.presentation.screens.rooms

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.softeq.blahblahrooms.R
import com.softeq.blahblahrooms.domain.models.Room
import com.softeq.blahblahrooms.presentation.components.EmptyListRooms
import com.softeq.blahblahrooms.presentation.components.RoomTile

@Composable
fun ListContent(
    rooms: List<Room>,
    onRoomClicked: (id: Int) -> Unit
) {
    if (rooms.isEmpty()) {
        EmptyListRooms(message = stringResource(id = R.string.empty_filter_rooms))
    }
    LazyColumn {
        items(rooms) { room ->
            RoomTile(room = room, onClick = onRoomClicked)
        }
    }
}