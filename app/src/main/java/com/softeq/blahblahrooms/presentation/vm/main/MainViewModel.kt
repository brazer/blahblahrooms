package com.softeq.blahblahrooms.presentation.vm.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softeq.blahblahrooms.domain.models.Room
import com.softeq.blahblahrooms.domain.usecases.SetUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val setUserIdUseCase: SetUserIdUseCase
) : ContainerHost<MainState, MainSideEffect>,
    ViewModel() {

    override val container = container<MainState, MainSideEffect>(MainState())
    private var userRooms: List<Room> = emptyList()

    init {
        initUserId()
    }

    private fun initUserId() {
        viewModelScope.launch {
            setUserIdUseCase.invoke()
        }
    }

    fun userRoomsChanged(rooms: List<Room>) = intent {
        userRooms = rooms
        if (userRooms.isNotEmpty()) {
            reduce { state.copy(isManageRoomButtonVisible = true) }
        }
    }

    fun manageRoomsButtonClicked() = intent {
        when (userRooms.size) {
            1 -> {
                postSideEffect(MainSideEffect.NavigateToRoomUpdateScreen(userRooms.first().id))
            }
            else -> {
                postSideEffect(MainSideEffect.NavigateToManageRoomsScreen)
            }
        }

    }
}

data class MainState(
    val isManageRoomButtonVisible: Boolean = false
)

sealed class MainSideEffect {
    object NavigateToManageRoomsScreen : MainSideEffect()
    data class NavigateToRoomUpdateScreen(val roomId: Int) : MainSideEffect()
}