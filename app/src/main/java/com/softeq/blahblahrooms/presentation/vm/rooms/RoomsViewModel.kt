package com.softeq.blahblahrooms.presentation.vm.rooms

import androidx.lifecycle.ViewModel
import com.softeq.blahblahrooms.domain.models.Room
import com.softeq.blahblahrooms.domain.usecases.FetchRoomsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class RoomsViewModel @Inject constructor(
    private val roomsUseCase: FetchRoomsUseCase
) : ContainerHost<RoomsState, RoomsSideEffect>, ViewModel() {

    override val container = container<RoomsState, RoomsSideEffect>(RoomsState.Loading)

    fun loadData() = intent {
        roomsUseCase.fetch().collectLatest {
            reduce { RoomsState.Loaded(it) }
        }
    }

}

sealed class RoomsState {
    object Loading: RoomsState()
    data class Loaded(val rooms: List<Room>): RoomsState()
}

class RoomsSideEffect