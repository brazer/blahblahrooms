package com.softeq.blahblahrooms.presentation.vm.roomdetails

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
class RoomDetailsViewModel @Inject constructor() :
    ContainerHost<RoomDetailsState, RoomDetailsSideEffect>, ViewModel() {

    override val container = container<RoomDetailsState, RoomDetailsSideEffect>(RoomDetailsState())

    fun setRoom(rooms: List<Room>, roomId: Int) = intent {
        reduce {
            state.copy(room = rooms.find { room ->
                room.id == roomId
            })
        }
    }

    fun backButtonClicked() = intent {
        postSideEffect(RoomDetailsSideEffect.BackToPreviousScreen)
    }
}

data class RoomDetailsState(
    val room: Room? = null
)

sealed class RoomDetailsSideEffect {
    object BackToPreviousScreen : RoomDetailsSideEffect()
}
