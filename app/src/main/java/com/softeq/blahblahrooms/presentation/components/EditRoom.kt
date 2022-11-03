package com.softeq.blahblahrooms.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
        TextField(
            value = room.price.toString(),
            onValueChange = {
                onPriceChanged(it.toFloatOrNull() ?: 0f)
            }
        )

        val latitude = room.location.latitude
        val longitude = room.location.longitude

        Text(text = context.getString(R.string.location))
        Row {
            Text(
                modifier = Modifier.weight(1f),
                text = context.getString(R.string.latitude)
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
                text = context.getString(R.string.longitude)
            )
            TextField(
                value = longitude.toString(),
                onValueChange = {
                    onLocationChanged(LatLng(latitude, it.toDoubleOrNull() ?: 0.0))
                },
                modifier = Modifier.weight(1f),
            )
        }


        Text(text = context.getString(R.string.address))
        TextField(
            value = room.address,
            onValueChange = {
                onAddressChanged(it)
            }
        )

        Text(text = context.getString(R.string.description))
        TextField(
            value = room.description,
            onValueChange = {
                onDescriptionChanged(it)
            }
        )

        Text(text = context.getString(R.string.period))
        Row {
            Text(text = context.getString(R.string.period_short))
            Switch(checked = room.period == Period.LONG, onCheckedChange = {
                onPeriodChanged(
                    if (it) {
                        Period.LONG
                    } else {
                        Period.SHORT
                    }
                )
            })
            Text(text = context.getString(R.string.period_long))
        }

        Text(text = context.getString(R.string.contacts))
        TextField(
            value = room.email,
            onValueChange = {
                onEmailChanged(it)
            }
        )
    }
}
