package com.homo_sapiens.ecosync.feature.auth.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homo_sapiens.ecosync.util.Screen
import com.homo_sapiens.ecosync.util.UiEvent
import com.homo_sapiens.ecosync.data.event.auth.SplashEvent
import com.homo_sapiens.ecosync.data.repository.auth.SplashRepositoryImpl
import com.homo_sapiens.ecosync.data.state.auth.SplashState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val splashRepository: SplashRepositoryImpl,
) : ViewModel() {
    private val _state = MutableStateFlow(SplashState())
    val state: StateFlow<SplashState> = _state

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent

    fun initUser() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = splashRepository.checkUser()) {
                is SplashEvent.OnNavigate -> {
                    delay(100L)
                    _uiEvent.emit(UiEvent.Navigate(result.route))
                }
                SplashEvent.ShowVerifyPopup -> _uiEvent.emit(UiEvent.Navigate(Screen.VerifyEmail.route))
            }
        }
    }
}