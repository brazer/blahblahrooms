package com.softeq.blahblahrooms.presentation.screens.orbit

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.softeq.blahblahrooms.presentation.route.NavigationArguments
import com.softeq.blahblahrooms.presentation.route.NavigationRoute
import com.softeq.blahblahrooms.presentation.route.navigateString
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun OrbitScreen(
    navController: NavController
) {

    val orbitViewModel: OrbitViewModel = hiltViewModel()
    val scaffoldState = rememberScaffoldState()
    val state = orbitViewModel.collectAsState()
    val context = LocalContext.current


    orbitViewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is OrbitSideEffect.Toast -> {
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
                onClick = {
                    orbitViewModel.addButtonClicked()
                }
            ) {
                Text("Add")
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
            Button(onClick = {
                navController.navigate(
                    NavigationRoute.ROUTE_GOOGLE_MAPS
                )
            }) {
                Text(text = "Go to Google Maps")
            }
        }
    }
}

@Preview
@Composable
fun PreviewOrbitScreen() {
    OrbitScreen(
        navController = NavController(LocalContext.current)
    )
}