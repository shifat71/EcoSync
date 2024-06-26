package com.homo_sapiens.ecosync.feature.polls

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homo_sapiens.ecosync.data.model.poll.Poll
import com.homo_sapiens.ecosync.data.repository.polls.PollsRepositoryImpl
import com.homo_sapiens.ecosync.data.state.poll.PollsState
import com.homo_sapiens.ecosync.util.POLL_PAGE_SIZE
import com.homo_sapiens.ecosync.util.Resource
import com.homo_sapiens.ecosync.util.Settings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PollsViewModel @Inject constructor(
    private val pollsRepository: PollsRepositoryImpl
) : ViewModel() {
    private val _state = MutableStateFlow(PollsState())
    val state: StateFlow<PollsState> = _state

    private val _getNext: Boolean
        get() = _state.value.isLoading.not() && _state.value.hasNext

    private val _canRefresh: Boolean
        get() = _state.value.isLoading.not()

    init {
        getPolls()
    }

    fun getPolls() = viewModelScope.launch(Dispatchers.IO) {
        pollsRepository.getPolls(_getNext).collectLatest {
            when (it) {
                is Resource.Error -> _state.value = _state.value.copy(
                    isLoading = false,
                    hasNext = false
                )
                is Resource.Loading -> _state.value = _state.value.copy(
                    isLoading = true
                )
                is Resource.Success -> _state.value = _state.value.copy(
                    isLoading = false,
                    polls = _state.value.polls.plus(it.data),
                    hasNext = it.data.count() > POLL_PAGE_SIZE - 1
                )
            }
        }
    }

    fun refreshPolls() = viewModelScope.launch(Dispatchers.IO) {
        pollsRepository.getPolls(_canRefresh, true).collectLatest {
            when (it) {
                is Resource.Error -> _state.value = _state.value.copy(
                    isLoading = false,
                    isRefreshing = false,
                    hasNext = false
                )
                is Resource.Loading -> _state.value = _state.value.copy(
                    isLoading = false,
                    isRefreshing = true,
                )
                is Resource.Success -> _state.value = _state.value.copy(
                    isLoading = false,
                    isRefreshing = false,
                    polls = it.data,
                    hasNext = it.data.count() > POLL_PAGE_SIZE - 1
                )
            }
        }
    }

    fun onOptionSelected(pollId: String, optionIndex: Int) = viewModelScope.launch(Dispatchers.IO) {
        val newList = _state.value.polls.map {
            if (it.id == pollId) {
                val newAnswers = it.answers
                newAnswers[Settings.currentUser.userId] = optionIndex
                Poll(
                    id = it.id,
                    title = it.title,
                    options = it.options,
                    answers = newAnswers,
                    createdAt = it.createdAt,
                    fromUser = it.fromUser
                )
            } else it
        }
        _state.value = _state.value.copy(
            polls = newList
        )
        pollsRepository.votePoll(pollId, optionIndex)
    }
}