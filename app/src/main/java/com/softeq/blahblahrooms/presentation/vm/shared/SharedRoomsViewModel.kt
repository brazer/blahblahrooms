package com.softeq.blahblahrooms.presentation.vm.shared

import androidx.lifecycle.ViewModel
import com.softeq.blahblahrooms.domain.models.Room
import com.softeq.blahblahrooms.domain.usecases.FetchRoomsUseCase
import com.softeq.blahblahrooms.domain.usecases.GetUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class SharedRoomsViewModel @Inject constructor(
    private val roomsUseCase: FetchRoomsUseCase,
    private val getUserIdUseCase: GetUserIdUseCase
) : ContainerHost<SharedRoomsState, SharedRoomsSideEffect>, ViewModel() {

    override val container = container<SharedRoomsState, SharedRoomsSideEffect>(SharedRoomsState())

    init {
        loadRooms()
    }

    private fun loadRooms() = intent {
        reduce {
            state.copy(isLoading = true)
        }
        roomsUseCase.fetch().collectLatest {
            reduce {
                state.copy(rooms = it)
            }
        }
        val userId = getUserIdUseCase.invoke()
        reduce {

            //todo delete when api is done
            state.copy(userRooms = state.rooms)

            //todo uncomment when api is done
//            state.copy(userRooms = state.rooms.filter { room ->
//                room.userId == userId
//            })
        }
        reduce {
            state.copy(isLoading = false)
        }
    }
}

data class SharedRoomsState(
    val isLoading: Boolean = false,
    val rooms: List<Room> = emptyList(),
    val userRooms: List<Room> = emptyList()
)

sealed class SharedRoomsSideEffect