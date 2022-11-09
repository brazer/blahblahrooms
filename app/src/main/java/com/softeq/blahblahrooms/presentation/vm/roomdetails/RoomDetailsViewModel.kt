package com.softeq.blahblahrooms.presentation.vm.roomdetails

import androidx.lifecycle.ViewModel
import com.softeq.blahblahrooms.domain.models.Room
import com.softeq.blahblahrooms.domain.usecases.GetRoomByIdUseCase
import com.softeq.blahblahrooms.presentation.vm.useCase
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class RoomDetailsViewModel @Inject constructor(
    private val getRoomByIdUseCase: GetRoomByIdUseCase
) :
    ContainerHost<RoomDetailsState, RoomDetailsSideEffect>, ViewModel() {

    override val container = container<RoomDetailsState, RoomDetailsSideEffect>(RoomDetailsState())

    fun setRoom(roomId: Int) = intent {
        useCase {
            getRoomByIdUseCase.invoke(roomId).collect {
                reduce { state.copy(room = it) }
            }
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
