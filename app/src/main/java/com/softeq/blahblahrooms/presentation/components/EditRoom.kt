package com.softeq.blahblahrooms.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.maps.model.LatLng
import com.softeq.blahblahrooms.R
import com.softeq.blahblahrooms.data.model.Period
import com.softeq.blahblahrooms.domain.models.Room

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditRoom(
    room: Room,
    onPriceChanged: (price: Float) -> Unit,
    onLocationChanged: (location: LatLng) -> Unit,
    onAddressChanged: (address: String) -> Unit,
    onCityChanged: (city: String) -> Unit,
    onDescriptionChanged: (description: String) -> Unit,
    onPeriodChanged: (period: Period) -> Unit,
    onEmailChanged: (email: String) -> Unit,
) {
    val state = rememberScrollState()
    Column(
        modifier = Modifier.verticalScroll(state)
    ) {

        Text(text = stringResource(R.string.price))
        TextField(
            value = room.price.toString(),
            onValueChange = {
                onPriceChanged(it.toFloatOrNull() ?: 0f)
            }
        )

        val latitude = room.location.latitude
        val longitude = room.location.longitude

        Text(text = stringResource(R.string.location))
        Row {
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.latitude)
            )
            TextField(
                value = latitude.toString(),
                onValueChange = {
                    onLocationChanged(LatLng(it.toDoubleOrNull() ?: 0.0, longitude))
                },
                modifier = Modifier.weight(1f),
            )
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.longitude)
            )
            TextField(
                value = longitude.toString(),
                onValueChange = {
                    onLocationChanged(LatLng(latitude, it.toDoubleOrNull() ?: 0.0))
                },
                modifier = Modifier.weight(1f),
            )
        }


        Text(text = stringResource(R.string.address))
        TextField(
            value = room.address,
            onValueChange = {
                onAddressChanged(it)
            }
        )

        Text(text = stringResource(R.string.city))
        TextField(
            value = room.city,
            onValueChange = {
                onCityChanged(it)
            }
        )

        Text(text = stringResource(R.string.description))
        TextField(
            value = room.description,
            onValueChange = {
                onDescriptionChanged(it)
            }
        )

        Text(text = stringResource(R.string.period))
        Row {
            Text(text = stringResource(R.string.period_short))
            Switch(checked = room.period == Period.LONG, onCheckedChange = {
                onPeriodChanged(
                    if (it) {
                        Period.LONG
                    } else {
                        Period.SHORT
                    }
                )
            })
            Text(text = stringResource(R.string.period_long))
        }

        Text(text = stringResource(R.string.contacts))
        TextField(
            value = room.email,
            onValueChange = {
                onEmailChanged(it)
            }
        )
    }
}

@Preview
@Composable
fun EditRoomPreview() {
    val room = Room(
        1, "1", 100.0f, LatLng(32.0, 54.0), "address",
        "city", Period.SHORT, "Description", "email"
    )
    Surface {
        EditRoom(
            room = room,
            onPriceChanged = {},
            onLocationChanged = {},
            onAddressChanged = {},
            onCityChanged = {},
            onDescriptionChanged = {},
            onPeriodChanged = {},
            onEmailChanged = {}
        )
    }
}
