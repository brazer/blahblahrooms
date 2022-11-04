package com.softeq.blahblahrooms.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.softeq.blahblahrooms.R
import com.softeq.blahblahrooms.data.providers.getPriceUSFormat
import com.softeq.blahblahrooms.domain.models.Room

@Composable
fun RoomTile(
    room: Room,
    onClick: (id: Int) -> Unit
) {
    Column(
        modifier = Modifier.clickable {
            onClick(room.id)
        }
    ) {
        Text(text = "${stringResource(id = R.string.price)} = ${room.getPriceUSFormat()}")
        Text(text = "${stringResource(id = R.string.location)} = ${room.location}")
        Text(text = "${stringResource(id = R.string.address)} = ${room.address}")
        Text(text = "${stringResource(id = R.string.period)} = ${room.period}")
        Text(text = "${stringResource(id = R.string.description)} = ${room.description}")
        Text(text = "${stringResource(id = R.string.contacts)} = ${room.email}")
        Spacer(modifier = Modifier.height(20.dp))
    }
}