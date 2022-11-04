package com.softeq.blahblahrooms.presentation.vm.managerooms

import androidx.lifecycle.ViewModel
import com.softeq.blahblahrooms.domain.models.Room
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class ManageRoomsViewModel @Inject constructor() :
    ContainerHost<ManageRoomsState, ManageRoomsSideEffect>, ViewModel() {

    override val container = container<ManageRoomsState, ManageRoomsSideEffect>(ManageRoomsState())

    fun setRooms(rooms: List<Room>) = intent {
        reduce {
            state.copy(rooms = rooms)
        }
    }

    fun roomClicked(roomId: Int) = intent {
        postSideEffect(ManageRoomsSideEffect.NavigateToUpdateRoomScreen(roomId))
    }
}

data class ManageRoomsState(
    val rooms: List<Room> = emptyList()
)

sealed class ManageRoomsSideEffect {
    data class NavigateToUpdateRoomScreen(val roomId: Int) : ManageRoomsSideEffect()
}