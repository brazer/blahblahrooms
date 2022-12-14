package com.softeq.blahblahrooms.presentation.vm.rooms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softeq.blahblahrooms.data.models.Period
import com.softeq.blahblahrooms.domain.models.Room
import com.softeq.blahblahrooms.domain.usecases.GetRoomsByFiltersUseCase
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
                            isVisibleFilters = false,
                            filterCounter = calculateFilterCounter(state)
                        )
                    }
                }
            }
        }
    }

    private fun calculateFilterCounter(state: RoomsState): Int {
        var counter = 0
        if (state.period != null) {
            counter++
        }
        if (state.city != null && state.city.isNotBlank()) {
            counter++
        }
        if (state.maxPrice != null || state.minPrice != null) {
            counter++
        }
        return counter
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
    val maxPrice: Double? = null,
    val filterCounter: Int = 0
)

sealed class RoomsSideEffect {
    data class NavigateToRoomDetailsScreen(val roomId: Int) : RoomsSideEffect()
}