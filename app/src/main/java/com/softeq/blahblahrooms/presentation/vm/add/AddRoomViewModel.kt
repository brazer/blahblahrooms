package com.softeq.blahblahrooms.presentation.vm.add

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.maps.model.LatLng
import com.softeq.blahblahrooms.App
import com.softeq.blahblahrooms.R
import com.softeq.blahblahrooms.data.providers.isValid
import com.softeq.blahblahrooms.domain.models.Period
import com.softeq.blahblahrooms.domain.models.Room
import com.softeq.blahblahrooms.domain.usecases.AddRoomUseCase
import com.softeq.blahblahrooms.presentation.EditRoomInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class AddRoomViewModel @Inject constructor(
    private val addRoomUseCase: AddRoomUseCase,
    application: Application
) : ContainerHost<AddRoomState, AddRoomSideEffect>, AndroidViewModel(application),
    EditRoomInterface {

    private var room = Room(-1, 0.0f, LatLng(0.0, 0.0), "",
        Period.SHORT, "", "")

    override val container = container<AddRoomState, AddRoomSideEffect>(AddRoomState.OnRoomChanged(room))

    fun addRoom() = intent {
        val invalidMessage = room.isValid(getContext().resources)
        if (invalidMessage == null) {
            reduce { AddRoomState.AddingOfRoom }
            addRoomUseCase.add(room).catch {
                val message = getContext().getString(R.string.room_not_added)
                intent { reduce { AddRoomState.OnRoomChanged(room) } }
                postSideEffect(AddRoomSideEffect.Toast(message, getContext()))
            }.collect {
                val message = getContext().getString(R.string.room_added)
                postSideEffect(AddRoomSideEffect.Toast(message, getContext()))
                postSideEffect(AddRoomSideEffect.RoomIsAdded)
            }
        } else postSideEffect(AddRoomSideEffect.Toast(invalidMessage, getContext()))
    }

    private fun getContext() = getApplication<App>()

    override fun onPriceChanged(price: Float) {
        room = room.copy(price = price)
        intent { reduce { AddRoomState.OnRoomChanged(room) } }
    }

    override fun onLocationChanged(location: LatLng) {
        room = room.copy(location = location)
        intent { reduce { AddRoomState.OnRoomChanged(room) } }
    }

    override fun onAddressChanged(address: String) {
        room = room.copy(address = address)
        intent { reduce { AddRoomState.OnRoomChanged(room) } }
    }

    override fun onDescriptionChanged(description: String) {
        room = room.copy(description = description)
        intent { reduce { AddRoomState.OnRoomChanged(room) } }
    }

    override fun onPeriodChanged(period: Period) {
        room = room.copy(period = period)
        intent { reduce { AddRoomState.OnRoomChanged(room) } }
    }

    override fun onEmailChanged(email: String) {
        room = room.copy(email = email)
        intent { reduce { AddRoomState.OnRoomChanged(room) } }
    }

}

sealed class AddRoomState {
    data class OnRoomChanged(val room: Room): AddRoomState()
    object AddingOfRoom: AddRoomState()
}

sealed class AddRoomSideEffect {
    object RoomIsAdded: AddRoomSideEffect()
    data class Toast(val message: String, val context: Context): AddRoomSideEffect()
}