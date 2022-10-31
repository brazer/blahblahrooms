package com.softeq.blahblahrooms.presentation.screens.arg

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun ArgScreen(
    navController: NavController,
    count: Int?
) {
    Scaffold {
        Column(
            modifier = Modifier.padding(it)
        ) {
            Text(text = "count = $count")
            Button(onClick = {
                navController.popBackStack()
            }) {
                Text(text = "Back")
            }
        }
    }
}