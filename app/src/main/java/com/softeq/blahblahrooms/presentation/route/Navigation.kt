package com.softeq.blahblahrooms.presentation.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.softeq.blahblahrooms.presentation.screens.arg.ArgScreen
import com.softeq.blahblahrooms.presentation.screens.googlemap.GoogleMapScreen
import com.softeq.blahblahrooms.presentation.screens.main.MainScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavigationRoute.ROUTE_ORBIT) {

        composable(NavigationRoute.ROUTE_ORBIT) {
            MainScreen(navController = navController)
        }

        composable(NavigationRoute.ROUTE_GOOGLE_MAPS) {
            GoogleMapScreen(navController = navController)
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