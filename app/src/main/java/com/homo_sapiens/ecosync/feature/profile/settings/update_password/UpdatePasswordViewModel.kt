package com.homo_sapiens.ecosync.feature.profile.settings.update_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homo_sapiens.ecosync.util.Resource
import com.homo_sapiens.ecosync.util.UiEvent
import com.homo_sapiens.ecosync.util.extension.isValidEmail
import com.homo_sapiens.ecosync.util.extension.isValidPassword
import com.homo_sapiens.ecosync.data.event.profile.UpdatePasswordEvent
import com.homo_sapiens.ecosync.data.repository.profile.UpdatePasswordRepositoryImpl
import com.homo_sapiens.ecosync.data.state.profile.UpdatePasswordState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdatePasswordViewModel @Inject constructor(
    private val updatePasswordRepository: UpdatePasswordRepositoryImpl
) : ViewModel() {

    private val _state = MutableStateFlow(UpdatePasswordState())
    val state: StateFlow<UpdatePasswordState> = _state

    private val _event = MutableSharedFlow<UiEvent>()
    val event: SharedFlow<UiEvent> = _event

    fun onEvent(event: UpdatePasswordEvent) {
        when (event) {
            is UpdatePasswordEvent.OnEmail -> _state.value = _state.value.copy(
                email = _state.value.email.copy(
                    text = event.email, error = ""
                )
            )
            is UpdatePasswordEvent.OnNewPassword -> _state.value = _state.value.copy(
                newPassword = _state.value.newPassword.copy(
                    text = event.password, error = ""
                )
            )
            is UpdatePasswordEvent.OnPassword -> _state.value = _state.value.copy(
                password = _state.value.password.copy(
                    text = event.password, error = ""
                )
            )
            UpdatePasswordEvent.OnToggleNewPassword -> _state.value = _state.value.copy(
                isNewPasswordVisible = _state.value.isNewPasswordVisible.not()
            )
            UpdatePasswordEvent.OnTogglePassword -> _state.value = _state.value.copy(
                isPasswordVisible = _state.value.isPasswordVisible.not()
            )
            UpdatePasswordEvent.OnReset -> resetPassword()
        }
    }

    private fun resetPassword() {
        viewModelScope.launch(Dispatchers.IO) {
            val email = _state.value.email.text
            val password = _state.value.password.text
            val newPassword = _state.value.newPassword.text

            if (email.isValidEmail().not()) {
                _state.value = _state.value.copy(
                    email = _state.value.email.copy(error = "Email is not valid."),
                )
                return@launch
            }
            if (password.isValidPassword().not()) {
                _state.value = _state.value.copy(
                    password = _state.value.password.copy(error = "Password is not valid."),
                )
                return@launch
            }
            if (newPassword.isValidPassword().not()) {
                _state.value = _state.value.copy(
                    newPassword = _state.value.newPassword.copy(error = "Password is not valid."),
                )
                return@launch
            }

            if (_state.value.isLoading) return@launch

            updatePasswordRepository.resetUserPassword(email, password, newPassword).collectLatest {
                when (it) {
                    is Resource.Error -> _state.value = _state.value.copy(
                        email = _state.value.email.copy(
                            error = it.data?.emailError ?: ""
                        ),
                        password = _state.value.password.copy(
                            error = it.data?.passwordError ?: ""
                        ), isLoading = false
                    )
                    is Resource.Loading -> _state.value = _state.value.copy(
                        isLoading = true
                    )
                    is Resource.Success -> _event.emit(UiEvent.ClearBackStack)
                }
            }
        }
    }
}