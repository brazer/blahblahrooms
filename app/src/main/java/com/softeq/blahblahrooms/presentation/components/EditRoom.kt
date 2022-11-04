package com.softeq.blahblahrooms.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.maps.model.LatLng
import com.softeq.blahblahrooms.R
import com.softeq.blahblahrooms.domain.models.Period
import com.softeq.blahblahrooms.domain.models.Room

@Composable
fun EditRoom(
    room: Room,
    onPriceChanged: (price: Float) -> Unit,
    onLocationChanged: (location: LatLng) -> Unit,
    onAddressChanged: (address: String) -> Unit,
    onDescriptionChanged: (description: String) -> Unit,
    onPeriodChanged: (period: Period) -> Unit,
    onEmailChanged: (email: String) -> Unit,
) {
    val state = rememberScrollState()
    val context = LocalContext.current
    Column(
        modifier = Modifier.verticalScroll(state)
    ) {

        Text(text = context.getString(R.string.price))
        var price by remember { mutableStateOf(room.price) }
        TextField(
            value = price.toString(),
            onValueChange = {
                price = it.toFloatOrNull() ?: 0f
                onPriceChanged(price)
            }
        )

        var latitude by remember { mutableStateOf(room.location.latitude) }
        var longitude by remember { mutableStateOf(room.location.longitude) }

        Text(text = context.getString(R.string.location))
        Row {
            Text(
                modifier = Modifier.weight(1f),
                text = context.getString(R.string.latitude)
            )
            TextField(
                value = latitude.toString(),
                onValueChange = {
                    latitude = it.toDoubleOrNull() ?: 0.0
                    onLocationChanged(LatLng(latitude, longitude))
                },
                modifier = Modifier.weight(1f),
            )
            Text(
                modifier = Modifier.weight(1f),
                text = context.getString(R.string.longitude)
            )
            TextField(
                value = longitude.toString(),
                onValueChange = {
                    longitude = it.toDoubleOrNull() ?: 0.0
                    onLocationChanged(LatLng(latitude, longitude))
                },
                modifier = Modifier.weight(1f),
            )
        }

        var address by remember { mutableStateOf(room.address) }
        Text(text = context.getString(R.string.address))
        TextField(
            value = address,
            onValueChange = {
                address = it
                onAddressChanged(address)
            }
        )

        Text(text = context.getString(R.string.description))
        var description by remember { mutableStateOf(room.description) }
        TextField(
            value = description,
            onValueChange = {
                description = it
                onDescriptionChanged(description)
            }
        )

        Text(text = context.getString(R.string.period))
        Row {
            Text(text = context.getString(R.string.period_short))
            var checked by remember { mutableStateOf(room.period == Period.LONG) }
            Switch(checked = checked, onCheckedChange = {
                checked = it
                onPeriodChanged(if (checked) Period.LONG else Period.SHORT)
            })
            Text(text = context.getString(R.string.period_long))
        }

        Text(text = context.getString(R.string.contacts))
        var email by remember { mutableStateOf(room.email) }
        TextField(
            value = email,
            onValueChange = {
                email = it
                onEmailChanged(email)
            }
        )
    }
}

@Preview
@Composable
fun EditRoomPreview() {
    val room = Room(1, 100.0f, LatLng(32.0, 54.0), "address",
        Period.SHORT, "Description", "email")
    Surface {
        EditRoom(
            room = room,
            onPriceChanged = {},
            onLocationChanged = {},
            onAddressChanged = {},
            onDescriptionChanged = {},
            onPeriodChanged = {},
            onEmailChanged = {}
        )
    }
}
