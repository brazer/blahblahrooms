package com.softeq.blahblahrooms.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.LatLng
import com.softeq.blahblahrooms.R
import com.softeq.blahblahrooms.data.models.Period
import com.softeq.blahblahrooms.data.providers.getPriceUSFormat
import com.softeq.blahblahrooms.domain.models.Room
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun RoomTile(
    room: Room,
    onClick: (id: Int) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                onClick(room.id)
            },
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = room.address)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = room.getPriceUSFormat())
            }
            Text(text = "${stringResource(id = R.string.description)}: \n ${room.description}")
            Text(text = "${stringResource(id = R.string.contacts)}: ${room.email}")
            Text(
                text = "${stringResource(id = R.string.location)}: lat = ${room.location.latitude}," +
                        " lng = ${room.location.longitude}"
            )
            val period = Json.encodeToString(room.period).replace("\"", "")
            Text(text = "${stringResource(id = R.string.period)}: $period")
        }
    }
}

@Preview
@Composable
fun PreviewRoomTile() {
    val room = Room(
        1, "1", 100.0f, LatLng(32.0, 54.0), "address",
        "city", Period.SHORT, "Description", "email"
    )

    Surface {
        RoomTile(room = room, onClick = {})
    }
}