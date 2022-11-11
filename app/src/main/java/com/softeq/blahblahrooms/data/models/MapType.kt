package com.softeq.blahblahrooms.data.models

enum class MapType {
    ROADMAP, SATELLITE, HYBRID, TERRAIN;

    override fun toString(): String {
        return when (this) {
            ROADMAP -> "roadmap"
            SATELLITE -> "satellite"
            HYBRID -> "hybrid"
            TERRAIN -> "terrain"
        }
    }

}