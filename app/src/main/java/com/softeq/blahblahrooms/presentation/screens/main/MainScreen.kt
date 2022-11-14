package com.softeq.blahblahrooms.presentation.screens.main

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.softeq.blahblahrooms.R
import com.softeq.blahblahrooms.presentation.components.ProgressView
import com.softeq.blahblahrooms.presentation.route.*
import com.softeq.blahblahrooms.presentation.vm.main.MainSideEffect
import com.softeq.blahblahrooms.presentation.vm.main.MainViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val mainViewModel: MainViewModel = hiltViewModel()
    val state = mainViewModel.collectAsState()
    val context = LocalContext.current

    var selectedItem by remember { mutableStateOf(1) }
    val tabs = listOf(
        NavigationBottomItem(
            icon = painterResource(id = R.drawable.ic_add_room),
            label = stringResource(id = R.string.add_room),
            route = NavigationRoute.ROUTE_ADD_ROOM
        ),
        NavigationBottomItem(
            icon = painterResource(id = R.drawable.ic_search_room),
            label = stringResource(id = R.string.rooms_flats),
            route = NavigationRoute.ROUTE_ROOMS
        ),
        NavigationBottomItem(
            icon = painterResource(id = R.drawable.ic_manage_room),
            label = stringResource(id = R.string.manage_rooms),
            route = NavigationRoute.ROUTE_MANAGE_ROOMS
        )
    )

    LaunchedEffect(key1 = null, block = {
        mainViewModel.initScreen(
            tabs
        )
    })

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    LaunchedEffect(key1 = currentDestination, block = {
        selectedItem = when (currentDestination?.route) {
            NavigationRoute.ROUTE_ADD_ROOM -> {
                0
            }
            NavigationRoute.ROUTE_ROOMS -> {
                1
            }
            NavigationRoute.ROUTE_MANAGE_ROOMS -> {
                2
            }
            else -> {
                1
            }
        }
    })

    mainViewModel.collectSideEffect() { sideEffect ->
        when (sideEffect) {
            MainSideEffect.NavigateToManageRoomsScreen -> {
                navController.navigate(NavigationRoute.ROUTE_MANAGE_ROOMS)
            }
            is MainSideEffect.NavigateToRoomUpdateScreen -> {
                navController.navigate(
                    navigateString(
                        NavigationRoute.ROUTE_ROOM_UPDATE,
                        Pair(NavigationArguments.ARGUMENT_ROOM_ID, sideEffect.roomId)
                    )
                )
            }
            MainSideEffect.ShowError -> {
                Toast.makeText(
                    context,
                    context.getText(R.string.default_error),
                    Toast.LENGTH_SHORT
                ).show()
            }
            is MainSideEffect.NavigateToRoute -> {
                navController.navigate(sideEffect.route)
            }
        }
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                state.value.tabs.forEachIndexed { index, tab ->
                    NavigationBarItem(
                        icon = { Icon(tab.icon, contentDescription = tab.label) },
                        label = { Text(tab.label) },
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                            mainViewModel.navigationTabClicked(tab.route)
                        }
                    )
                }
            }
        }
    ) {
        Box(
            modifier = Modifier.padding(it)
        ) {
            Navigation(navController = navController)
        }
        if (state.value.isLoading) {
            ProgressView()
        }
    }
}