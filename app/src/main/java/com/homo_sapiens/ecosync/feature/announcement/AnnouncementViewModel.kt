package com.homo_sapiens.ecosync.feature.announcement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homo_sapiens.ecosync.data.announcementList
import com.homo_sapiens.ecosync.util.Settings
import com.homo_sapiens.ecosync.data.model.announcement.Announcement
import com.homo_sapiens.ecosync.data.state.announcement.AnnouncementState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import javax.inject.Inject
@HiltViewModel
class AnnouncementViewModel @Inject constructor(

) : ViewModel() {
    private val _state = MutableStateFlow(AnnouncementState())
    val state: StateFlow<AnnouncementState> = _state

    init {
        getAnnouncements()
    }

    private fun getAnnouncements() {
        viewModelScope.launch(Dispatchers.IO) {

            val announcements = mutableListOf<Announcement>()

            announcementList.forEach { element ->
                announcements.add(element)
            }
            _state.value = _state.value.copy(
                announcements = announcements,
                isLoading = false,
                isRefreshing = false,
                showEmptyDepartmentView = false
            )
        }
    }

    fun refreshAnnouncements() {
        viewModelScope.launch {
            if (_state.value.isLoading.not() && _state.value.isRefreshing.not()) {
                _state.value = _state.value.copy(isRefreshing = true)
                getAnnouncements()
            }
        }
    }

    fun showAnnouncementPopup(item: Announcement) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isPopupActive = true,
                activeAnnouncement = item
            )
        }
    }

    fun closePopup() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isPopupActive = false,
                activeAnnouncement = Announcement()
            )
        }
    }
}