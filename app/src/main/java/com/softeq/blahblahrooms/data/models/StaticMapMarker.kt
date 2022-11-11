package com.softeq.blahblahrooms.data.models

import com.google.android.gms.maps.model.LatLng

data class StaticMapMarker(
    private val location: LatLng,
    /**
     * black, brown, green, purple, yellow, blue, gray, orange, red, white
     */
    val color: Color,
    val label: Char
) {

    fun getLatitude() = location.latitude.toString()

    fun getLongitude() = location.longitude.toString()

    enum class Color {
        BLACK, BROWN, GREEN, PURPLE, YELLOW, BLUE, GRAY, ORANGE, RED, WHITE;

        override fun toString(): String {
            return when(this) {
                BLACK -> "black"
                GREEN -> "green"
                RED -> "red"
                BLUE -> "blue"
                BROWN -> "brown"
                PURPLE -> "purple"
                YELLOW -> "yellow"
                GRAY -> "gray"
                ORANGE -> "orange"
                WHITE -> "white"
            }
        }
    }

}