package com.softeq.blahblahrooms.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.softeq.blahblahrooms.presentation.screens.orbit.OrbitScreen
import com.softeq.blahblahrooms.presentation.screens.orbit.OrbitViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OrbitScreen(orbitViewModel = OrbitViewModel())
        }
    }
}