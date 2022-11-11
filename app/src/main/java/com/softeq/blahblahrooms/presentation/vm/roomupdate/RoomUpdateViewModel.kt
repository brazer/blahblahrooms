package com.softeq.blahblahrooms.presentation.vm.roomupdate

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.maps.model.LatLng
import com.softeq.blahblahrooms.App
import com.softeq.blahblahrooms.data.models.Period
import com.softeq.blahblahrooms.data.providers.isValid
import com.softeq.blahblahrooms.domain.models.Room
import com.softeq.blahblahrooms.domain.usecases.GetRoomByIdUseCase
import com.softeq.blahblahrooms.domain.usecases.UpdateRoomUseCase
import com.softeq.blahblahrooms.presentation.EditRoomInterface
import com.softeq.blahblahrooms.presentation.vm.useCase
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class RoomUpdateViewModel @Inject constructor(
    private val updateRoomUseCase: UpdateRoomUseCase,
    private val getRoomByIdUseCase: GetRoomByIdUseCase,
    application: Application
) : ContainerHost<RoomUpdateState, RoomUpdateSideEffect>, AndroidViewModel(application),
    EditRoomInterface {

    override val container = container<RoomUpdateState, RoomUpdateSideEffect>(RoomUpdateState())

    fun setRoom(roomId: Int) = intent {
        useCase {
            getRoomByIdUseCase.invoke(roomId).collect {
                reduce { state.copy(room = it) }
            }
        }
    }

    override fun roomPriceChanged(price: Float) = intent {
        reduce {
            state.copy(room = state.room?.copy(price = price))
        }
    }

    override fun roomLocationChanged(location: LatLng) = intent {
        reduce {
            state.copy(room = state.room?.copy(location = location))
        }
    }

    override fun roomAddressChanged(address: String) = intent {
        reduce {
            state.copy(room = state.room?.copy(address = address))
        }
    }

    override fun roomCityChanged(city: String) = intent {
        reduce {
            state.copy(room = state.room?.copy(city = city))
        }
    }

    override fun roomDescriptionChanged(description: String) = intent {
        reduce {
            state.copy(room = state.room?.copy(description = description))
        }
    }

    override fun roomPeriodChanged(period: Period) = intent {
        reduce {
            state.copy(room = state.room?.copy(period = period))
        }
    }

    override fun roomEmailChanged(email: String) = intent {
        reduce {
            state.copy(room = state.room?.copy(email = email))
        }
    }

    fun saveButtonClicked() = intent {
        useCase {
            state.room?.let {
                reduce { state.copy(isLoading = true) }
                val errorMessage = it.isValid(getContext().resources)
                if (errorMessage != null) {
                    postSideEffect(RoomUpdateSideEffect.ShowError(errorMessage))
                } else {
                    updateRoomUseCase.invoke(room = it)
                }
                reduce { state.copy(isLoading = false) }
            }
            postSideEffect(RoomUpdateSideEffect.BackToPreviousScreen)
        }.onFailure {
            postSideEffect(RoomUpdateSideEffect.ShowError())
            reduce { state.copy(isLoading = false) }
        }
    }

    private fun getContext() = getApplication<App>()

    fun cancelButtonClicked() = intent {
        postSideEffect(RoomUpdateSideEffect.BackToPreviousScreen)
    }
}

data class RoomUpdateState(
    val room: Room? = null,
    val isLoading: Boolean = false
)

sealed class RoomUpdateSideEffect {
    object BackToPreviousScreen : RoomUpdateSideEffect()
    data class ShowError(val errorMessage: String? = null) : RoomUpdateSideEffect()
}