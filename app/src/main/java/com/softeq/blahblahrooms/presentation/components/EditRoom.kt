package com.softeq.blahblahrooms.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.android.gms.maps.model.LatLng
import com.softeq.blahblahrooms.R
import com.softeq.blahblahrooms.data.models.Period
import com.softeq.blahblahrooms.data.models.StaticMapMarker
import com.softeq.blahblahrooms.data.staticmap.StaticMap
import com.softeq.blahblahrooms.domain.models.Room
import com.softeq.blahblahrooms.presentation.pxToDp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditRoom(
    room: Room,
    editable: Boolean,
    onPriceChanged: (price: Float) -> Unit,
    onLocationChanged: (location: LatLng) -> Unit,
    onAddressChanged: (address: String) -> Unit,
    onDescriptionChanged: (description: String) -> Unit,
    onPeriodChanged: (period: Period) -> Unit,
    onEmailChanged: (email: String) -> Unit,
) {
    Column {
        val decimalKeyboard = remember {
            KeyboardOptions(keyboardType = KeyboardType.Decimal)
        }
        val verticalPadding = 4.dp
        val horizontalPadding = 4.dp

        var vPrice by remember { mutableStateOf(room.price.toString()) }
        OutlinedTextField(
            label = {
                Text(
                    text = stringResource(R.string.price),
                    style = MaterialTheme.typography.labelLarge
                )
            },
            value = vPrice,
            modifier = Modifier.padding(vertical = verticalPadding),
            textStyle = MaterialTheme.typography.bodyMedium,
            readOnly = !editable,
            keyboardOptions = decimalKeyboard,
            singleLine = true,
            onValueChange = {
                vPrice = it
                onPriceChanged(it.toFloatOrNull() ?: 0f)
            }
        )

        val latitude = room.location.latitude
        val longitude = room.location.longitude

        Text(
            text = stringResource(R.string.location),
            style = MaterialTheme.typography.labelLarge
        )
        val context = LocalContext.current
        val widthPx = 640
        val heightPx = 496
        val mapUrl = remember(room.location) {
            buildStaticMapUrl(room.location, widthPx, heightPx)
        }
        val height = remember { context.pxToDp(heightPx).times(1.3f) }
        val width = remember { context.pxToDp(widthPx).times(1.3f) }
        AsyncImage(
            model = mapUrl,
            modifier = Modifier
                .height(height)
                .width(width)
                .padding(vertical = verticalPadding),
            placeholder = painterResource(id = R.drawable.location_placeholder),
            contentScale = ContentScale.FillBounds,
            contentDescription = null
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            var vLat by remember { mutableStateOf(latitude.toString()) }
            OutlinedTextField(
                label = {
                    Text(
                        text = stringResource(R.string.latitude),
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                value = vLat,
                readOnly = !editable,
                textStyle = MaterialTheme.typography.bodyMedium,
                singleLine = true,
                keyboardOptions = decimalKeyboard,
                onValueChange = {
                    vLat = it
                    onLocationChanged(LatLng(it.toDoubleOrNull() ?: 0.0, longitude))
                },
                modifier = Modifier
                    .padding(horizontal = horizontalPadding)
                    .weight(1f)
            )
            var vLon by remember { mutableStateOf(longitude.toString()) }
            OutlinedTextField(
                label = {
                    Text(
                        text = stringResource(R.string.longitude),
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                value = vLon,
                readOnly = !editable,
                textStyle = MaterialTheme.typography.bodyMedium,
                singleLine = true,
                keyboardOptions = decimalKeyboard,
                onValueChange = {
                    vLon = it
                    onLocationChanged(LatLng(latitude, it.toDoubleOrNull() ?: 0.0))
                },
                modifier = Modifier
                    .padding(horizontal = horizontalPadding)
                    .weight(1f)
            )
        }

        OutlinedTextField(
            label = {
                Text(
                    text = stringResource(R.string.address),
                    style = MaterialTheme.typography.labelLarge
                )
            },
            value = room.address,
            modifier = Modifier
                .padding(vertical = verticalPadding)
                .fillMaxWidth(),
            readOnly = !editable,
            textStyle = MaterialTheme.typography.bodyMedium,
            shape = MaterialTheme.shapes.medium,
            onValueChange = {
                onAddressChanged(it)
            }
        )

        OutlinedTextField(
            label = {
                Text(
                    text = stringResource(R.string.description),
                    style = MaterialTheme.typography.labelLarge
                )
            },
            value = room.description,
            modifier = Modifier
                .padding(vertical = verticalPadding)
                .fillMaxWidth(),
            readOnly = !editable,
            textStyle = MaterialTheme.typography.bodyMedium,
            onValueChange = {
                onDescriptionChanged(it)
            }
        )

        Text(
            text = stringResource(R.string.period),
            style = MaterialTheme.typography.labelLarge
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(R.string.period_short),
                style = MaterialTheme.typography.labelMedium
            )
            Switch(
                modifier = Modifier.padding(horizontal = horizontalPadding),
                checked = room.period == Period.LONG,
                enabled = editable,
                onCheckedChange = {
                onPeriodChanged(
                    if (it) {
                        Period.LONG
                    } else {
                        Period.SHORT
                    }
                )
            })
            Text(
                text = stringResource(R.string.period_long),
                style = MaterialTheme.typography.labelMedium
            )
        }

        OutlinedTextField(
            label = {
                Text(
                    text = stringResource(R.string.contacts),
                    style = MaterialTheme.typography.labelLarge
                )
            },
            value = room.email,
            modifier = Modifier
                .padding(vertical = verticalPadding)
                .fillMaxWidth(),
            readOnly = !editable,
            textStyle = MaterialTheme.typography.bodyMedium,
            onValueChange = {
                onEmailChanged(it)
            }
        )
    }
}

private fun buildStaticMapUrl(markerLocation: LatLng, widthPx: Int, heightPx: Int): String {
    val marker = StaticMapMarker(
        location = markerLocation,
        color = StaticMapMarker.Color.ORANGE,
        label = 'R'
    )
    return StaticMap(
        width = widthPx,
        height = heightPx
    ).addMarkers(marker).buildUrl()
}

@Preview
@Composable
fun EditRoomPreview() {
    val room = Room(
        1, "1", 100.0f, LatLng(32.0, 54.0), "address",
        Period.SHORT, "Description", "email"
    )
    Surface {
        EditRoom(
            room = room,
            editable = true,
            onPriceChanged = {},
            onLocationChanged = {},
            onAddressChanged = {},
            onDescriptionChanged = {},
            onPeriodChanged = {},
            onEmailChanged = {}
        )
    }
}
