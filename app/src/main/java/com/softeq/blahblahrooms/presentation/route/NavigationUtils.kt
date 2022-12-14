package com.softeq.blahblahrooms.presentation.route

object NavigationRoute {
    const val ROUTE_ROOMS = "route_rooms"
    const val ROUTE_ADD_ROOM = "route_add_room"
    const val ROUTE_MANAGE_ROOMS = "route_manage_rooms"
    const val ROUTE_ROOM_UPDATE = "route_room_update"
    const val ROUTE_ROOM_DETAILS = "route_room_details"
}

object NavigationArguments {
    const val ARGUMENT_ROOM_ID = "room_id"
}

fun destinationString(route: String, vararg arguments: String): String {
    var dest = route
    var isFirstArg = true
    arguments.forEach {
        if (!isFirstArg) {
            dest += ","
        } else {
            isFirstArg = !isFirstArg
        }
        dest += "?$it={$it}"
    }
    return dest
}

fun navigateString(route: String, vararg arguments: Pair<String, Any>): String {
    var dest = route
    var isFirstArg = true
    arguments.forEach {
        if (!isFirstArg) {
            dest += ","
        } else {
            isFirstArg = !isFirstArg
        }
        dest += "?${it.first}=${it.second}"
    }
    return dest
}