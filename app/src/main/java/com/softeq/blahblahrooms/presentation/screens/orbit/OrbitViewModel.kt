package com.softeq.blahblahrooms.presentation.screens.orbit

import androidx.lifecycle.ViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class OrbitViewModel() : ContainerHost<OrbitState, OrbitSideEffect>, ViewModel() {

    override val container = container<OrbitState, OrbitSideEffect>(OrbitState(sum = 0))

    fun addButtonClicked() = intent {
        postSideEffect(OrbitSideEffect.Toast("Add button clicked"))

        reduce {
            state.copy(sum = state.sum + 1)
        }
    }
}

data class OrbitState(
    val sum: Int
)

sealed class OrbitSideEffect {
    data class Toast(val text: String) : OrbitSideEffect()
}