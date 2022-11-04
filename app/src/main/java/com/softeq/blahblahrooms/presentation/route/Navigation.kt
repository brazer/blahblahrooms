package com.softeq.blahblahrooms.presentation.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.softeq.blahblahrooms.presentation.screens.add.AddRoomScreen
import com.softeq.blahblahrooms.presentation.screens.arg.ArgScreen
import com.softeq.blahblahrooms.presentation.screens.main.MainScreen
import com.softeq.blahblahrooms.presentation.screens.rooms.RoomsScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavigationRoute.ROUTE_MAIN) {

        composable(NavigationRoute.ROUTE_MAIN) {
            MainScreen(navController = navController)
        }

        composable(NavigationRoute.ROUTE_ROOMS) {
            RoomsScreen(navController = navController)
        }

        composable(NavigationRoute.ROUTE_ADD_ROOM) {
            AddRoomScreen(navController = navController)
        }

        composable(
            destinationString(NavigationRoute.ROUTE_ARG, NavigationArguments.ARGUMENT_COUNT),
            arguments = listOf(
                navArgument(NavigationArguments.ARGUMENT_COUNT) {
                    nullable = false
                    defaultValue = 0
                    type = NavType.IntType
                }
            )
        ) {
            ArgScreen(
                navController = navController,
                count = it.arguments?.getInt(NavigationArguments.ARGUMENT_COUNT)
            )
        }
    }
}