package com.softeq.blahblahrooms.presentation.screens.rooms

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.softeq.blahblahrooms.domain.models.Room
import com.softeq.blahblahrooms.presentation.components.RoomTile

@Composable
fun ListContent(
    rooms: List<Room>,
    onRoomClicked: (id: Int) -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            items(rooms) { room ->
                RoomTile(room = room, onClick = { id ->
                    onRoomClicked(id)
                })
            }
        }
    }
}