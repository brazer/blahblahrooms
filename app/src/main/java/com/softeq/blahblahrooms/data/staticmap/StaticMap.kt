package com.softeq.blahblahrooms.data.staticmap

import com.softeq.blahblahrooms.BuildConfig
import com.softeq.blahblahrooms.data.DataConfig
import com.softeq.blahblahrooms.data.models.ImageFormat
import com.softeq.blahblahrooms.data.models.MapType
import com.softeq.blahblahrooms.data.models.StaticMapMarker

data class StaticMap(
    val baseUrl: String = DataConfig.BASE_URL_STATIC_MAP_API,
    /**
     * Center (required if markers not present) defines the center of the map,
     * equidistant from all edges of the map. This parameter takes a location as either a comma-separated
     * {latitude,longitude} pair (e.g. "40.714728,-73.998672") or a string address
     * (e.g. "city hall, new york, ny") identifying a unique location on the face of the earth.
     */
    val center: String? = null,
    /**
     * Zoom (required if markers not present) defines the zoom level of the map, which determines
     * the magnification level of the map. This parameter takes a numerical value corresponding
     * to the zoom level of the region desired.
     */
    val zoom: Int? = null,
    /**
     * See getSize() method.
     */
    val width: Int = 600,
    /**
     * See getSize() method.
     */
    val height: Int = 300,
    /**
     * Maptype (optional) defines the type of map to construct. There are several possible maptype
     * values, including roadmap, satellite, hybrid, and terrain.
     */
    val mapType: MapType? = null,
    /**
     * Scale (optional) affects the number of pixels that are returned. scale=2 returns twice as
     * many pixels as scale=1 while retaining the same coverage area and level of detail
     * (i.e. the contents of the map don't change). This is useful when developing for
     * high-resolution displays. The default value is 1. Accepted values are 1 and 2.
     */
    val scale: Int? = null,
    /**
     * Format (optional) defines the format of the resulting image. By default, the Maps Static
     * API creates PNG images. There are several possible formats including GIF, JPEG and PNG types.
     * Which format you use depends on how you intend to present the image. JPEG typically provides
     * greater compression, while GIF and PNG provide greater detail.
     */
    val format: ImageFormat? = null,
    /**
     * language (optional) defines the language to use for display of labels on map tiles.
     * Note that this parameter is only supported for some country tiles; if the specific
     * language requested is not supported for the tile set, then the default language for
     * that tileset will be used.
     */
    val language: String? = null,
    val key: String = BuildConfig.API_KEY,
    val signature: String? = null
) {

    private val markers = HashSet<StaticMapMarker>()

    fun addMarkers(vararg markers: StaticMapMarker): StaticMap {
        markers.forEach { marker ->
            this.markers.add(marker)
        }
        return this
    }

    /**
     * Size (required) defines the rectangular dimensions of the map image. This parameter takes a
     * string of the form {horizontal_value}x{vertical_value}. For example, 500x400 defines a map
     * 500 pixels wide by 400 pixels high. Maps smaller than 180 pixels in width will display a
     * reduced-size Google logo. This parameter is affected by the scale parameter; the final
     * output size is the product of the size and scale values.
     * Example: size=600x300
     */
    private fun getSize(): String {
        return "${width}x$height"
    }

    fun buildUrl(): String {
        val builder = StringBuilder(baseUrl)
        builder.append("?size=${getSize()}")
        if (center != null) {
            builder.append("&center=$center")
        }
        if (zoom != null) {
            builder.append("&zoom=$zoom")
        }
        if (mapType != null) {
            builder.append("&maptype=$mapType")
        }
        if (scale != null) {
            builder.append("&scale=$scale")
        }
        if (format != null) {
            builder.append("&format=$format")
        }
        if (language != null) {
            builder.append("&language=$language")
        }
        if (markers.isNotEmpty()) {
            val separator = "%7C"
            markers.forEach { marker ->
                builder.append("&markers=color:${marker.color}${separator}label:${marker.label}")
                builder.append("$separator${marker.getLatitude()},${marker.getLongitude()}")
            }
        }
        builder.append("&key=$key")
        if (signature != null) {
            builder.append("&signature=$signature")
        }
        return builder.toString()
    }

}

