package com.homo_sapiens.ecosync.data.state.profile

import com.homo_sapiens.ecosync.data.model.auth.Faculty

data class DepartmentState(
    val faculty: List<Faculty> = emptyList()
) {
    val isLoading = faculty.isEmpty()
}
