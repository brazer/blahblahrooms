package com.softeq.blahblahrooms.presentation.vm.rooms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softeq.blahblahrooms.data.model.Period
import com.softeq.blahblahrooms.domain.models.Room
import com.softeq.blahblahrooms.domain.usecases.GetRoomsByFiltersUseCase
import com.softeq.blahblahrooms.domain.usecases.GetRoomsUseCase
import com.softeq.blahblahrooms.domain.usecases.GetUserCityLocationUseCase
import com.softeq.blahblahrooms.presentation.vm.useCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class RoomsViewModel @Inject constructor(
    private val getRoomsUseCase: GetRoomsUseCase,
    private val getUserCityLocationUseCase: GetUserCityLocationUseCase,
    private val getRoomsByFiltersUseCase: GetRoomsByFiltersUseCase,
) : ContainerHost<RoomsState, RoomsSideEffect>, ViewModel() {

    override val container = container<RoomsState, RoomsSideEffect>(RoomsState())

    init {
        loadData()
    }

    private var jobGetRooms: Job? = null

    private fun loadData() = intent {
        useCase {
            reduce { state.copy(isLoading = true) }
            jobGetRooms = viewModelScope.launch {
                getRoomsByFiltersUseCase.invoke(
                    state.period,
                    state.city,
                    state.minPrice,
                    state.maxPrice
                ).collect { rooms ->
                    reduce { state.copy(rooms = rooms, isLoading = false) }
                }
            }
        }
    }

    fun roomClicked(id: Int) = intent {
        postSideEffect(RoomsSideEffect.NavigateToRoomDetailsScreen(id))
    }

    fun backButtonClicked() = intent {
        postSideEffect(RoomsSideEffect.BackToPreviousScreen)
    }

    fun filtersButtonClicked() = intent {
        reduce { state.copy(isVisibleFilters = !state.isVisibleFilters) }
        useCase {
            if (state.city == null) {
                reduce { state.copy(city = getUserCityLocationUseCase.invoke()) }
            }
        }
    }

    fun periodShortClicked() = intent {
        reduce { state.copy(period = Period.SHORT) }
    }

    fun periodLongClicked() = intent {
        reduce { state.copy(period = Period.LONG) }
    }

    fun periodBothClicked() = intent {
        reduce { state.copy(period = null) }
    }

    fun cityFilterChanged(city: String) = intent {
        reduce { state.copy(city = city) }
    }

    fun minPriceFilterChanged(value: Double?) = intent {
        reduce { state.copy(minPrice = value) }
    }

    fun maxPriceFilterChanged(value: Double?) = intent {
        reduce { state.copy(maxPrice = value) }
    }

    fun applyButtonClicked() = intent {
        useCase {
            reduce { state.copy(isLoading = true) }
            jobGetRooms?.cancel()
            jobGetRooms = viewModelScope.launch {
                getRoomsByFiltersUseCase.invoke(
                    state.period,
                    state.city,
                    state.minPrice,
                    state.maxPrice
                ).collect { rooms ->
                    reduce {
                        state.copy(
                            rooms = rooms,
                            isLoading = false,
                            isVisibleFilters = false
                        )
                    }
                }
            }
        }
    }

    fun dismissFiltersRequest() = intent {
        reduce { state.copy(isVisibleFilters = false) }
    }

}

data class RoomsState(
    val rooms: List<Room> = emptyList(),
    val isLoading: Boolean = false,
    val period: Period? = null,
    val isVisibleFilters: Boolean = false,
    val city: String? = null,
    val minPrice: Double? = null,
    val maxPrice: Double? = null
)

sealed class RoomsSideEffect {
    object BackToPreviousScreen : RoomsSideEffect()
    data class NavigateToRoomDetailsScreen(val roomId: Int) : RoomsSideEffect()
}