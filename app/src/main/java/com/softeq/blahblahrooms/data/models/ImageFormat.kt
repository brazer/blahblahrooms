package com.softeq.blahblahrooms.data.models

enum class ImageFormat {
    PNG_PNG8, PNG32, GIF, JPG, JPG_BASELINE;

    override fun toString(): String {
        return when(this) {
            PNG_PNG8 -> "png"
            PNG32 -> "png32"
            GIF -> "gif"
            JPG -> "jpg"
            JPG_BASELINE -> "jpg-baseline"
        }
    }
}