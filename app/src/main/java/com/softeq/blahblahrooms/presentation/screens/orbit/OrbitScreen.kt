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
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun OrbitScreen() {

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
        }
    }
}

@Preview
@Composable
fun PreviewOrbitScreen() {
    OrbitScreen()
}