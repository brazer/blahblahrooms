package com.softeq.blahblahrooms.presentation.route

object NavigationRoute {
    const val ROUTE_MAIN = "route_main"
    const val ROUTE_ROOMS = "route_rooms"
    const val ROUTE_ADD_ROOM = "route_add_room"
    const val ROUTE_ARG = "route_arg"
}

object NavigationArguments {
    const val ARGUMENT_COUNT = "count"
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