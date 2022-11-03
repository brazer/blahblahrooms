package com.softeq.blahblahrooms.presentation.vm.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ContainerHost<MainState, MainSideEffect>,
    ViewModel() {

    override val container = container<MainState, MainSideEffect>(MainState(sum = 0))

    fun addButtonClicked() = intent {
        postSideEffect(MainSideEffect.Toast("Add button clicked"))

        reduce {
            state.copy(sum = state.sum + 1)
        }
    }
}

data class MainState(
    val sum: Int
)

sealed class MainSideEffect {
    data class Toast(val text: String) : MainSideEffect()
}