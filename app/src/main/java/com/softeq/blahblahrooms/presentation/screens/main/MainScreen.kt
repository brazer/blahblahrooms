package com.softeq.blahblahrooms.presentation.screens.main

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.softeq.blahblahrooms.R
import com.softeq.blahblahrooms.presentation.route.NavigationArguments
import com.softeq.blahblahrooms.presentation.route.NavigationRoute
import com.softeq.blahblahrooms.presentation.route.navigateString
import com.softeq.blahblahrooms.presentation.vm.main.MainSideEffect
import com.softeq.blahblahrooms.presentation.vm.main.MainViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun MainScreen(
    navController: NavController
) {

    val mainViewModel: MainViewModel = hiltViewModel()
    val scaffoldState = rememberScaffoldState()
    val state = mainViewModel.collectAsState()
    val context = LocalContext.current

    mainViewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is MainSideEffect.Toast -> {
                Toast.makeText(context, sideEffect.text, Toast.LENGTH_SHORT).show()
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
    ) {
        Column(modifier = Modifier.padding(it)) {
            Text("Sum = ${state.value.sum}")
            Button(
                onClick = mainViewModel::addButtonClicked
            ) {
                Text("Add")
            }
            Button(onClick = {
                navController.navigate(
                    NavigationRoute.ROUTE_ADD_ROOM
                )
            }) {
                Text(text = stringResource(id = R.string.add_room))
            }
            Button(onClick = {
                navController.navigate(NavigationRoute.ROUTE_ROOMS)
            }) {
                Text(stringResource(id = R.string.rooms_flats))
            }
            Button(onClick = {
                navController.navigate(
                    navigateString(
                        NavigationRoute.ROUTE_ARG,
                        Pair(
                            NavigationArguments.ARGUMENT_COUNT,
                            state.value.sum
                        )
                    )
                )
            }) {
                Text(text = "Go to ArgScreen")
            }
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