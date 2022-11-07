package com.softeq.blahblahrooms.presentation.vm.rooms

import androidx.lifecycle.ViewModel
import com.softeq.blahblahrooms.domain.models.Room
import com.softeq.blahblahrooms.domain.usecases.FetchRoomsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class RoomsViewModel @Inject constructor(
    private val roomsUseCase: FetchRoomsUseCase
) : ContainerHost<RoomsState, RoomsSideEffect>, ViewModel() {

    override val container = container<RoomsState, RoomsSideEffect>(RoomsState())

    init {
        loadData()
    }

    private fun loadData() = intent {
        reduce { state.copy(isLoading = true) }
        roomsUseCase.fetch().collectLatest {
            reduce { state.copy(rooms = it) }
        }
        reduce { state.copy(isLoading = false) }
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