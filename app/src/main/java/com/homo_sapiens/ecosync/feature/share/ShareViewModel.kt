package com.homo_sapiens.ecosync.feature.share

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homo_sapiens.ecosync.core.data.event.CropperEvent
import com.homo_sapiens.ecosync.util.UiEvent
import com.homo_sapiens.ecosync.data.event.share.ShareEvent
import com.homo_sapiens.ecosync.data.repository.share.ShareRepositoryImpl
import com.homo_sapiens.ecosync.data.state.share.ShareState
import com.homo_sapiens.ecosync.util.Resource
import com.homo_sapiens.ecosync.util.extension.getImageUri
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShareViewModel @Inject constructor(
    private val shareRepository: ShareRepositoryImpl
) : ViewModel() {

    private val _state = MutableStateFlow(ShareState())
    val state: StateFlow<ShareState> = _state

    private val _event = MutableSharedFlow<UiEvent>()
    val event: SharedFlow<UiEvent> = _event

    fun onEvent(event: ShareEvent) {
        viewModelScope.launch {
            when (event) {
                is ShareEvent.OnImage -> _state.value = _state.value.copy(
                    selectedImage = event.uri.toString()
                )
                is ShareEvent.OnBody -> if (event.text.count() <= 250) {
                    _state.value = _state.value.copy(
                        bodyState = _state.value.bodyState.copy(
                            text = event.text
                        )
                    )
                }
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

    fun sharePost() {
        viewModelScope.launch(Dispatchers.IO) {
            val body = _state.value.bodyState.text
            val image = _state.value.postImage
            if (body.isEmpty() && image.isEmpty()) return@launch

            shareRepository.sharePost(body, image).collectLatest {
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