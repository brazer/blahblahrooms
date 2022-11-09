package com.softeq.blahblahrooms.presentation.vm.add

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.maps.model.LatLng
import com.softeq.blahblahrooms.App
import com.softeq.blahblahrooms.data.providers.isValid
import com.softeq.blahblahrooms.domain.models.Period
import com.softeq.blahblahrooms.domain.models.Room
import com.softeq.blahblahrooms.domain.usecases.AddRoomUseCase
import com.softeq.blahblahrooms.domain.usecases.GetUserIdUseCase
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
class AddRoomViewModel @Inject constructor(
    private val addRoomUseCase: AddRoomUseCase,
    private val getUserIdUseCase: GetUserIdUseCase,
    application: Application
) : ContainerHost<AddRoomState, AddRoomSideEffect>, AndroidViewModel(application),
    EditRoomInterface {

    override val container =
        container<AddRoomState, AddRoomSideEffect>(AddRoomState(room = initialRoom))

    init {
        initUserId()
    }

    private fun initUserId() = intent {
        useCase {
            val userId = getUserIdUseCase.invoke()
            userId?.let {
                reduce { state.copy(room = state.room.copy(userId = userId)) }
            }
        }
    }

    fun addRoom() = intent {
        useCase {
            reduce { state.copy(isLoading = true) }
            val errorMessage = state.room.isValid(getContext().resources)
            if (errorMessage != null) {
                postSideEffect(AddRoomSideEffect.ShowError(errorMessage))
            } else {
                addRoomUseCase.invoke(state.room)
            }
            reduce { state.copy(isLoading = false) }
            postSideEffect(AddRoomSideEffect.NavigatedBack)
        }
    }

    private fun getContext() = getApplication<App>()

    fun onBackButtonClicked() = intent {
        postSideEffect(AddRoomSideEffect.NavigatedBack)
    }

    override fun roomPriceChanged(price: Float) = intent {
        reduce { state.copy(room = state.room.copy(price = price)) }
    }


    override fun roomLocationChanged(location: LatLng) = intent {
        reduce { state.copy(room = state.room.copy(location = location)) }
    }

    override fun roomAddressChanged(address: String) = intent {
        reduce { state.copy(room = state.room.copy(address = address)) }
    }

    override fun roomDescriptionChanged(description: String) = intent {
        reduce { state.copy(room = state.room.copy(description = description)) }
    }

    override fun roomPeriodChanged(period: Period) = intent {
        reduce { state.copy(room = state.room.copy(period = period)) }
    }

    override fun roomEmailChanged(email: String) = intent {
        reduce { state.copy(room = state.room.copy(email = email)) }
    }

    companion object {
        val initialRoom = Room(
            id = 0,
            userId = "",
            price = 0f,
            location = LatLng(0.0, 0.0),
            address = "",
            period = Period.SHORT,
            description = "",
            email = ""
        )
    }
}

data class AddRoomState(
    val room: Room,
    val isLoading: Boolean = false
)

sealed class AddRoomSideEffect {
    object NavigatedBack : AddRoomSideEffect()
    data class ShowError(val errorMessage: String) : AddRoomSideEffect()
}