package com.homo_sapiens.ecosync.feature.profile.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homo_sapiens.ecosync.util.Resource
import com.homo_sapiens.ecosync.util.Settings
import com.homo_sapiens.ecosync.util.UiEvent
import com.homo_sapiens.ecosync.data.repository.profile.ProfileRepositoryImpl
import com.homo_sapiens.ecosync.data.state.profile.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    _stateHandle: SavedStateHandle,
    private val profileRepository: ProfileRepositoryImpl
) : ViewModel() {
    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state

    private val _event = MutableSharedFlow<UiEvent>()
    val event: SharedFlow<UiEvent> = _event

    init {
        val userId = _stateHandle.get<String>("userId") ?: ""
        _state.value = _state.value.copy(
            userId = userId
        )
        getUserData(userId)
    }

    private fun getUserData(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (_state.value.isSelf) _state.value = _state.value.copy(
                user = Settings.currentUser.toUser(),
                isLoading = false
            )
            profileRepository.getUser(userId).collectLatest {
                when (it) {
                    is Resource.Error -> Unit
                    is Resource.Loading -> _state.value = _state.value.copy(
                        isLoading = true
                    )
                    is Resource.Success -> _state.value = _state.value.copy(
                        user = it.data,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun updateCurrentUser() {
        viewModelScope.launch {
            if (_state.value.isSelf)
                _state.value = _state.value.copy(
                    user = Settings.currentUser.toUser()
                )
        }
    }
}