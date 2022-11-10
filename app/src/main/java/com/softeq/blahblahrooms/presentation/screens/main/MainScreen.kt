package com.softeq.blahblahrooms.presentation.screens.main

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.softeq.blahblahrooms.R
import com.softeq.blahblahrooms.presentation.components.ProgressView
import com.softeq.blahblahrooms.presentation.components.TopBlahBlahRoomsBar
import com.softeq.blahblahrooms.presentation.route.NavigationArguments
import com.softeq.blahblahrooms.presentation.route.NavigationRoute
import com.softeq.blahblahrooms.presentation.route.navigateString
import com.softeq.blahblahrooms.presentation.vm.main.MainSideEffect
import com.softeq.blahblahrooms.presentation.vm.main.MainViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController
) {

    val mainViewModel: MainViewModel = hiltViewModel()
    val state = mainViewModel.collectAsState()
    val context = LocalContext.current

    mainViewModel.collectSideEffect { sideEffect ->
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
            MainSideEffect.NavigateToAddRoomScreen -> {
                navController.navigate(
                    NavigationRoute.ROUTE_ADD_ROOM
                )
            }
            MainSideEffect.NavigateToRoomsScreen -> {
                navController.navigate(NavigationRoute.ROUTE_ROOMS)
            }
        }
    }

    Scaffold(
        topBar = { TopBlahBlahRoomsBar(title = stringResource(id = R.string.home)) }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                mainViewModel.addRoomsButtonClicked()
            }) {
                Text(text = stringResource(id = R.string.add_room))
            }
            Button(onClick = {
                mainViewModel.roomsButtonClicked()
            }) {
                Text(stringResource(id = R.string.rooms_flats))
            }
            if (state.value.isManageRoomButtonVisible) {
                Button(onClick = {
                    mainViewModel.manageRoomsButtonClicked()
                }) {
                    Text(text = stringResource(id = R.string.manage_rooms))
                }
            }
        }
        if (state.value.isLoading) {
            ProgressView()
        }
    }
}

@Preview
@Composable
fun PreviewMainScreen() {
    MainScreen(
        navController = NavController(LocalContext.current)
    )
}