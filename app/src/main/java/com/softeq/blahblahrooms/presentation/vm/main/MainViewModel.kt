package com.softeq.blahblahrooms.presentation.vm.main

import androidx.lifecycle.ViewModel
import com.softeq.blahblahrooms.domain.models.Room
import com.softeq.blahblahrooms.domain.usecases.GetRoomsByUserIdUseCase
import com.softeq.blahblahrooms.domain.usecases.LoadRoomsUseCase
import com.softeq.blahblahrooms.domain.usecases.SetUserIdUseCase
import com.softeq.blahblahrooms.presentation.route.NavigationBottomItem
import com.softeq.blahblahrooms.presentation.route.NavigationRoute
import com.softeq.blahblahrooms.presentation.vm.useCase
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val setUserIdUseCase: SetUserIdUseCase,
    private val loadRoomsUseCase: LoadRoomsUseCase,
    private val getRoomsByUserIdUseCase: GetRoomsByUserIdUseCase,
) : ContainerHost<MainState, MainSideEffect>,
    ViewModel() {

    override val container = container<MainState, MainSideEffect>(MainState())
    private var userRooms: List<Room> = emptyList()

    init {
        initUserId()
        loadData()
    }

    private fun loadData() = intent {
        useCase {
            reduce { state.copy(isLoading = true) }
            loadRoomsUseCase.invoke()
            reduce { state.copy(isLoading = false) }
        }.onFailure {
            postSideEffect(MainSideEffect.ShowError)
            reduce { state.copy(isLoading = false) }
        }
    }

    private fun initUserId() = intent {
        useCase {
            setUserIdUseCase.invoke()
        }.onFailure {
            postSideEffect(MainSideEffect.ShowError)
        }
    }

    fun initScreen(
        tabs: List<NavigationBottomItem>
    ) = intent {
        useCase {
            getRoomsByUserIdUseCase.invoke().collect {
                userRooms = it
                reduce { state.copy(tabs = tabs) }
            }
        }
    }

    fun navigationTabClicked(route: String) = intent {
        if (route == NavigationRoute.ROUTE_MANAGE_ROOMS) {
            when (userRooms.size) {
                1 -> {
                    postSideEffect(MainSideEffect.NavigateToRoomUpdateScreen(userRooms.first().id))
                }
                else -> {
                    postSideEffect(MainSideEffect.NavigateToManageRoomsScreen)
                }
            }
        } else {
            postSideEffect(MainSideEffect.NavigateToRoute(route = route))
        }
    }
}

data class MainState(
    val isLoading: Boolean = false,
    val tabs: List<NavigationBottomItem> = emptyList()
)

sealed class MainSideEffect {
    object NavigateToManageRoomsScreen : MainSideEffect()
    data class NavigateToRoomUpdateScreen(val roomId: Int) : MainSideEffect()
    object ShowError : MainSideEffect()
    data class NavigateToRoute(val route: String) : MainSideEffect()
}