package com.softeq.blahblahrooms.presentation.vm.managerooms

import androidx.lifecycle.ViewModel
import com.softeq.blahblahrooms.domain.models.Room
import com.softeq.blahblahrooms.domain.usecases.GetRoomsByUserIdUseCase
import com.softeq.blahblahrooms.presentation.vm.useCase
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class ManageRoomsViewModel @Inject constructor(
    private val getRoomsByUserIdUseCase: GetRoomsByUserIdUseCase
) :
    ContainerHost<ManageRoomsState, ManageRoomsSideEffect>, ViewModel() {

    override val container = container<ManageRoomsState, ManageRoomsSideEffect>(ManageRoomsState())

    init {
        loadRooms()
    }

    private fun loadRooms() = intent {
        useCase {
            getRoomsByUserIdUseCase.invoke().collect {
                reduce { state.copy(rooms = it) }
            }
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