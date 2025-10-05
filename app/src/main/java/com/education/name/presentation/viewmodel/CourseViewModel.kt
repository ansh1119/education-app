package com.education.name.presentation.viewmodel


import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.education.name.domain.usecase.AddCourseUseCase
import com.education.name.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseViewModel @Inject constructor(
    private val addCourseUseCase: AddCourseUseCase
) : ViewModel() {

    private val _addCourseState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val addCourseState: StateFlow<UiState<Unit>> = _addCourseState

    fun addCourse(name: String, description: String, imageUri: Uri?) {
        viewModelScope.launch {
            _addCourseState.value = UiState.Loading
            val result = addCourseUseCase(name, description, imageUri)
            _addCourseState.value = result.fold(
                onSuccess = { UiState.Success(Unit) },
                onFailure = { UiState.Error(it.message ?: "Failed to add course") }
            )
        }
    }
}
