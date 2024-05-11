package com.homo_sapiens.ecosync.feature.profile.settings.edit_profile.component.department

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homo_sapiens.ecosync.util.Resource
import com.homo_sapiens.ecosync.data.repository.profile.DepartmentRepositoryImpl
import com.homo_sapiens.ecosync.data.state.profile.DepartmentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DepartmentViewModel @Inject constructor(
    private val departmentRepository: DepartmentRepositoryImpl
): ViewModel() {
    private val _state = MutableStateFlow(DepartmentState())
    val state: StateFlow<DepartmentState> = _state

    init {
        if (_state.value.isLoading) getFaculties()
    }

    private fun getFaculties() {
        viewModelScope.launch(Dispatchers.IO) {
            departmentRepository.getFaculties().collectLatest {
                when (it) {
                    is Resource.Error -> Unit
                    is Resource.Loading -> Unit
                    is Resource.Success -> _state.value = _state.value.copy(
                        faculty = it.data,
                    )
                }
            }
        }
    }
}