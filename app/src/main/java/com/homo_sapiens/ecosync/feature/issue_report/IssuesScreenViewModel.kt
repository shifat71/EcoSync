package com.homo_sapiens.ecosync.feature.issue_report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homo_sapiens.ecosync.core.data.event.CropperEvent
import com.homo_sapiens.ecosync.data.event.share.IssueEvent
import com.homo_sapiens.ecosync.util.UiEvent
import com.homo_sapiens.ecosync.util.Resource
import com.homo_sapiens.ecosync.util.Settings
import com.homo_sapiens.ecosync.util.extension.getImageUri
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IssuesScreenViewModel @Inject constructor(
    private val issuesScreenRepository: IssuesScreenRepositoryImpl
) : ViewModel() {

    private val _state = MutableStateFlow(IssueState())
    val state: StateFlow<IssueState> = _state

    private val _event = MutableSharedFlow<UiEvent>()
    val event: SharedFlow<UiEvent> = _event

    fun onEvent(event: IssueEvent) {
        viewModelScope.launch {
            when (event) {
                is IssueEvent.OnImage -> _state.value = _state.value.copy(
                    selectedImage = event.uri.toString()
                )
                is IssueEvent.OnBody -> if (event.text.count() <= 250) {
                    _state.value = _state.value.copy(
                        bodyState = _state.value.bodyState.copy(
                            text = event.text
                        )
                    )
                }
                is IssueEvent.OnLocation -> _state.value = _state.value.copy(
                        location = _state.value.location.copy(
                            text = event.text
                        )
                    )

                is IssueEvent.IsAnonymous -> _state.value = _state.value.copy(
                    isAnonymous = event.mark
                )

            }
        }
    }

    fun onCropperEvent(event: CropperEvent) {
        when (event) {
            is CropperEvent.OnCropFinish -> {
                val uri = event.context.getImageUri(event.bitmap)
                _state.value = _state.value.copy(
                    postImage = uri.toString(),
                    selectedImage = ""
                )
            }
            CropperEvent.OnCropCancel -> _state.value = _state.value.copy(
                selectedImage = "",
            )
            else -> Unit
        }
    }

    fun submit() {
        viewModelScope.launch(Dispatchers.IO) {
            val body = _state.value.bodyState.text
            val image = _state.value.postImage
            val location = _state.value.location.text
            val isAnonymous = _state.value.isAnonymous
            val userId = Settings.currentUser.userId

            if (body.isEmpty() && image.isEmpty()) return@launch

            issuesScreenRepository.submit(
                body = body,
                 image = image,
                location = location,
                userId = userId,
                isAnonymous = isAnonymous,
            ).collectLatest {
                when (it) {
                    is Resource.Error -> _state.value = _state.value.copy(
                        isLoading = false
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