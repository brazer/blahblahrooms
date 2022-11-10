package com.softeq.blahblahrooms.presentation.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.softeq.blahblahrooms.presentation.screens.add.AddRoomScreen
import com.softeq.blahblahrooms.presentation.screens.main.MainScreen
import com.softeq.blahblahrooms.presentation.screens.managerooms.ManageRoomsScreen
import com.softeq.blahblahrooms.presentation.screens.roomdetails.RoomDetailsScreen
import com.softeq.blahblahrooms.presentation.screens.rooms.RoomsScreen
import com.softeq.blahblahrooms.presentation.screens.roomupdate.RoomUpdateScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavigationRoute.ROUTE_MAIN) {

        composable(NavigationRoute.ROUTE_MAIN) {
            MainScreen(
                navController = navController
            )
        }

        composable(NavigationRoute.ROUTE_ROOMS) {
            RoomsScreen(navController = navController)
        }

        composable(
            destinationString(
                NavigationRoute.ROUTE_ROOM_DETAILS,
                NavigationArguments.ARGUMENT_ROOM_ID
            ),
            arguments = listOf(
                navArgument(NavigationArguments.ARGUMENT_ROOM_ID) {
                    nullable = false
                    defaultValue = 0
                    type = NavType.IntType
                }
            )
        ) {
            RoomDetailsScreen(
                navController = navController,
                roomId = it.arguments?.getInt(NavigationArguments.ARGUMENT_ROOM_ID) ?: 0
            )
        }

        composable(NavigationRoute.ROUTE_ADD_ROOM) {
            AddRoomScreen(navController = navController)
        }

        composable(NavigationRoute.ROUTE_MANAGE_ROOMS) {
            ManageRoomsScreen(
                navController = navController
            )
        }

        composable(
            destinationString(
                NavigationRoute.ROUTE_ROOM_UPDATE,
                NavigationArguments.ARGUMENT_ROOM_ID
            ),
            arguments = listOf(
                navArgument(NavigationArguments.ARGUMENT_ROOM_ID) {
                    nullable = false
                    defaultValue = 0
                    type = NavType.IntType
                }
            )
        ) {
            RoomUpdateScreen(
                navController = navController,
                roomId = it.arguments?.getInt(NavigationArguments.ARGUMENT_ROOM_ID) ?: 0
            )
        }
    }
}