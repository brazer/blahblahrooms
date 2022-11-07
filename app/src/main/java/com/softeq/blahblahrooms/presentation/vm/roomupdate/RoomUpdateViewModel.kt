package com.softeq.blahblahrooms.presentation.vm.roomupdate

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.maps.model.LatLng
import com.softeq.blahblahrooms.App
import com.softeq.blahblahrooms.data.providers.isValid
import com.softeq.blahblahrooms.domain.models.Period
import com.softeq.blahblahrooms.domain.models.Room
import com.softeq.blahblahrooms.domain.usecases.UpdateRoomUseCase
import com.softeq.blahblahrooms.presentation.EditRoomInterface
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
    application: Application
) : ContainerHost<RoomUpdateState, RoomUpdateSideEffect>, AndroidViewModel(application),
    EditRoomInterface {

    override val container = container<RoomUpdateState, RoomUpdateSideEffect>(RoomUpdateState())

    fun setRoom(rooms: List<Room>, roomId: Int) = intent {
        reduce {
            state.copy(room = rooms.find { room ->
                room.id == roomId
            })
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

        state.room?.let {
            reduce { state.copy(isLoading = true) }
            val errorMessage = it.isValid(getContext().resources)
            if (errorMessage != null) {
                postSideEffect(RoomUpdateSideEffect.ShowError(errorMessage))
            } else {
                updateRoomUseCase.invoke(room = it)
            }
            reduce { state.copy(isLoading = true) }
        }
        postSideEffect(RoomUpdateSideEffect.BackToPreviousScreen)
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
    data class ShowError(val errorMessage: String) : RoomUpdateSideEffect()
}