package com.softeq.blahblahrooms.presentation.vm.rooms

import androidx.lifecycle.ViewModel
import com.softeq.blahblahrooms.domain.models.Room
import com.softeq.blahblahrooms.domain.usecases.GetRoomsUseCase
import com.softeq.blahblahrooms.presentation.vm.useCase
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class RoomsViewModel @Inject constructor(
    private val getRoomsUseCase: GetRoomsUseCase
) : ContainerHost<RoomsState, RoomsSideEffect>, ViewModel() {

    override val container = container<RoomsState, RoomsSideEffect>(RoomsState())

    init {
        loadData()
    }

    private fun loadData() = intent {
        useCase {
            reduce { state.copy(isLoading = true) }
            getRoomsUseCase.invoke().collect { rooms ->
                reduce { state.copy(rooms = rooms, isLoading = false) }
            }
        }
    }

    fun roomClicked(id: Int) = intent {
        postSideEffect(RoomsSideEffect.NavigateToRoomDetailsScreen(id))
    }

    fun backButtonClicked() = intent {
        postSideEffect(RoomsSideEffect.BackToPreviousScreen)
    }

}

data class RoomsState(
    val rooms: List<Room> = emptyList(),
    val isLoading: Boolean = false
)

sealed class RoomsSideEffect {
    object BackToPreviousScreen : RoomsSideEffect()
    data class NavigateToRoomDetailsScreen(val roomId: Int) : RoomsSideEffect()
}